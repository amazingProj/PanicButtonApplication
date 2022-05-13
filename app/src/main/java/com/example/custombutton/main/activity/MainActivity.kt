package com.example.custombutton.main.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.os.Handler
import android.net.wifi.WifiManager
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.custombutton.R
import com.example.custombutton.main.ui.TextFeedbackClass
import com.example.custombutton.main.model.*
import com.example.custombutton.main.service.SocketSender
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
import com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import java.nio.charset.StandardCharsets.UTF_8;
import java.util.*


/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {

    private var battery : Float = 0f

    private val receiver:BroadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.apply {
                battery = currentBatteryCharge
            }
        }
    }

    val Intent.currentBatteryCharge: Float
        get() {
            // integer containing the maximum battery level
            val scale = getIntExtra(
                BatteryManager.EXTRA_SCALE, -1
            )

            //  integer field containing the current battery
            //  level, from 0 to EXTRA_SCALE
            val level = getIntExtra(
                BatteryManager.EXTRA_LEVEL, -1
            )

            // return current battery charge percentage
            return level * 100 / scale.toFloat()
        }
    /**
     * event handler for handeling all the event when user hit the panic button
     */
    private var eventHandler: EventHandler = EventHandler()

    /**
     * handler class responsible for to do certain task every x seconds
     */
    private var handler: Handler = Handler()

    private val FIVE_SECONDS = 5000 * 6
    val topic = "mqtt/android/wifi/messages"

    /**
     * that property responsible for the delay
     */
    private val delay: Int = FIVE_SECONDS

    /**
     * path for sending information routes
     */
    private val routePathWifiAccessPoint = "wifiInformation"

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

        // initialize a new intent filter instance
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)

        // register the broadcast receiver
        registerReceiver(receiver,filter)

    }

    override fun onStart() {
        super.onStart()

        // create an socket.io connection
        SocketSender.createConnection()

        var host = "712d6a94edd544ddac8b5c44600f18d3.s1.eu.hivemq.cloud";
        var username = "Esp32";
        var password = "Esp32Asaf";


        // Building the client with ssl.

        var client= MqttClient.builder()
            .useMqttVersion5()
            .serverHost(host)
            .serverPort(8884)
            .sslWithDefaultConfig()
            .webSocketConfig()
            .serverPath("mqtt")
            .applyWebSocketConfig()
            .buildBlocking();


        //  Connect securely with username, password.

        client.connectWith()
            .simpleAuth()
            .username(username)
            .password(UTF_8.encode(password))
            .applySimpleAuth()
            .send();

        println("Connected successfully");

        /**
         * Subscribe to the topic "my/test/topic" with qos = 2 and print the received message.
         */
        client.subscribeWith()
            .topicFilter("ack/android")
            .qos(MqttQos.EXACTLY_ONCE)
            .send();

        /**
         * Set a callback that is called when a message is received (using the async API style).
         * Then disconnect the client after a message was received.
         */
        client.toAsync().publishes(ALL, fun (publish) {
            print("Received message: " + publish + " -> " + UTF_8.decode(publish.payload.get()));
        });

        newClient = client
    }

    override fun onResume() {
        handler.postDelayed(object : Runnable {
            override fun run() {

                sendToMqttBroker()
                handler.postDelayed(this, Integer.toUnsignedLong(delay))
            }
        }, Integer.toUnsignedLong(delay))
        super.onResume()
    }


    fun sendToMqttBroker()
    {
        val singleton = InformationClass()

        singleton.setBattery(battery)
        singleton.setIsAlarmed(alarmed.boolean)

        // getting the wifi manger class
        var wifiManager = applicationContext.getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
        var wifiInfo = wifiManager!!.getConnectionInfo()

        var bool : Boolean = wifiManager.startScan()


        // adding into singleton the access points that were found
        if (bool){
            wifiManager.scanResults.forEach{
                var accessPoint : AccessPoint = AccessPoint()
                accessPoint.setBssid(it.BSSID)
                accessPoint.setRssi(it.level)
                singleton.addAccessPoint(accessPoint)
            }
        }

        val str : String = singleton.toSend()

        Log.d("size", wifiManager.scanResults.size.toString())
        Log.d("str", str)

        if (singleton.getNumberOfAccessPoint() != 0)
        {
            // Publish the wifi scan to mqtt broker cloud

            newClient?.publishWith()
                ?.topic(topic)
                ?.payload(UTF_8.encode(singleton.toSend()))
                ?.qos(MqttQos.EXACTLY_ONCE)
                ?.send();

            // sending the data to server
            SocketSender.sendDataToServer(routePathWifiAccessPoint, str);
        }
    }

    /************************* Functions *****************************/

    /**
     * notifies all observers of eventHandler
     * @param view - a view button
     * @notice we must view parameter because this is how it defines
     *         in activity_main.xml with the view/button
     */
    fun notifyObservers(view: View) {
        if (!alarmed.boolean)
        {
            sendToMqttBroker()
        }
        alarmed.boolean = true
        eventHandler.notifyALlObservers(applicationContext)
    }

    fun movetoSettingActivity(view: View){
        startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        finish()
    }
}