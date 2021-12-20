package com.example.custombutton.main.activity

import com.example.custombutton.main.model.AccessPoint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import com.example.custombutton.main.service.SocketHandler
import com.example.custombutton.main.model.EmergencySignal
import android.widget.TextView
import android.util.Log
import android.os.Handler
import android.R.attr
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
import android.R.attr.delay
import android.widget.Toast
import android.content.Context
import com.example.custombutton.main.service.SocketSender


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

    /**
     * delays contstants
     */
    private val QUICKLY_RUNNING_QUATER_SEC = 250
    private val HALF_MINUTE_MILI_SEC = 30000
    private val FIVE_SECONDS = 5000

    /**
     * that property responsible for the delay
     */
    private val delay: Int = HALF_MINUTE_MILI_SEC

    /**
     * path for sending information routes
     */
    private val routePathWifiAccessPoint = "wifiInformation"

    private val routePathMacAddress = "macAddress"

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
        // send the mac address of the device to the server
        SocketSender.sendDataToServer(routePathMacAddress, getMac(applicationContext))

        // setting the singleton object with the mac address of the device
        val singleton: InformationClass = InformationClass.instance
        singleton.setMac(getMac(applicationContext))
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

                // deltes all the access points from singleton
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

    /**
     * gets the mac address of the device for identify goals
     * @param context - the application context
     * @return a string represents the mac address of the device
     */
    fun getMac(context: Context): String {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo
        return info.macAddress.toUpperCase()
    }
}