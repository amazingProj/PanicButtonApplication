package com.example.custombutton.main.activity

import com.example.custombutton.main.model.AccessPoint
import io.github.cdimascio.dotenv.dotenv
import android.content.Intent
import com.example.custombutton.main.model.EmergencySignal
import android.util.Log
import android.os.Handler
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.custombutton.R
import com.example.custombutton.main.model.Alarm
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.ui.TextFeedbackClass
import com.example.custombutton.main.model.InformationClass
import android.net.wifi.WifiInfo
import com.example.custombutton.main.service.SocketSender
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
import com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import java.nio.charset.StandardCharsets.UTF_8;


/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {

    /**
     * event handler for handeling all the event when user hit the panic button
     */
    private var eventHandler: EventHandler = EventHandler()

    /**
     * handler class responsible for to do certain task every x seconds
     */
    private var handler: Handler = Handler()
   // lateinit var client : Mqtt5Client

    /**
     * delays contstants
     */
    private val QUICKLY_RUNNING_QUATER_SEC = 250
    private val HALF_MINUTE_MILI_SEC = 30000
    private val FIVE_SECONDS = 5000
    val topic = "mqtt/android/wifi/messages"

    /**
     * that property responsible for the delay
     */
    private val delay: Int = HALF_MINUTE_MILI_SEC

    /**
     * path for sending information routes
     */
    private val routePathWifiAccessPoint = "wifiInformation"

    private val routePathMacAddress = "macAddress"

    private var newClient : Mqtt5BlockingClient ?= null

    /**
     * creates ui of the screen which in activity_main
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // attach alarm and text feedback to event handler class

        eventHandler.attachManyObservers(
            Alarm(eventHandler), TextFeedbackClass(eventHandler, this),
            EmergencySignal(eventHandler)
        );

        // create an socket.io connection
        SocketSender.createConnection()

        var host = "712d6a94edd544ddac8b5c44600f18d3.s1.eu.hivemq.cloud";
        var username = "Esp32";
        var password = "Esp32Asaf";

        /**
         * Building the client with ssl.
         */
        var client= MqttClient.builder()
            .useMqttVersion5()
            .serverHost(host)
            .serverPort(8884)
            .sslWithDefaultConfig()
            .webSocketConfig()
            .serverPath("mqtt")
            .applyWebSocketConfig()
            .buildBlocking();

        /**
         * Connect securely with username, password.
         */
        client.connectWith()
            .simpleAuth()
            .username(username)
            .password(UTF_8.encode(password))
            .applySimpleAuth()
            .send();

        System.out.println("Connected successfully");

        /**
         * Subscribe to the topic "my/test/topic" with qos = 2 and print the received message.
         */
        client.subscribeWith()
            .topicFilter("my/test/topic")
            .qos(MqttQos.EXACTLY_ONCE)
            .send();

        /**
         * Set a callback that is called when a message is received (using the async API style).
         * Then disconnect the client after a message was received.
         */
        client.toAsync().publishes(ALL, fun (publish) {
            print("Received message: " + publish + " -> " + UTF_8.decode(publish.payload.get()));

            client.disconnect();
        });

        newClient = client
    }

    /**
     * sending the location each of x seconds
     */
    override fun onResume() {
        // here we delayed the on resume function
        handler.postDelayed(object : Runnable {
            override fun run() {
                // getting the wifi manger class
                var wifiManager = applicationContext.getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
                var wifiInfo = wifiManager!!.getConnectionInfo()
                val singleton : InformationClass = InformationClass.instance
                var bool : Boolean = wifiManager.startScan()

                // adding into singleton the access points that were found
                if (bool){
                    wifiManager.scanResults.forEach{
                        var accessPoint : AccessPoint = AccessPoint()
                        accessPoint.setFrequency(it.frequency)
                        accessPoint.setBssid(it.BSSID)
                        accessPoint.setSsid(it.SSID)
                        accessPoint.setRssi(it.level)
                        singleton.addAccessPoint(accessPoint)
                    }
                }

                Log.d("size", wifiManager.scanResults.size.toString())
                Log.d("singleton", singleton.toSend())

                // sending the data to server
                SocketSender.sendDataToServer(routePathWifiAccessPoint, singleton.toSend());

                /**
                 * Publish the wifi scan to mqtt broker cloud
                 */
                newClient?.publishWith()
                    ?.topic(topic)
                    ?.payload(UTF_8.encode(singleton.toSend()))
                    ?.qos(MqttQos.EXACTLY_ONCE)
                    ?.send();

                // deletes all the access points from singleton
                singleton.newAccessPoints()
                handler.postDelayed(this, Integer.toUnsignedLong(delay))
            }
        }, Integer.toUnsignedLong(delay))
        super.onResume()
    }

    /************************* Functions *****************************/

    /**
     * notifies all observers of eventHandler
     * @param view - a view button
     * @notice we must view parameter because this is how it defines
     *         in activity_main.xml with the view/button
     */
    fun notifyObservers(view: View) {
        eventHandler.notifyALlObservers(applicationContext)
    }

    fun movetoSettingActivity(view: View){
        startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        finish()
    }
}