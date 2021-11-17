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


/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {

    /**
     * event handler for handeling all the event when user hit the panic button
     */
    private var eventHandler : EventHandler = EventHandler()

    /**
     * handler class responsible for to do certain task every x seconds
     */
    private var handler : Handler = Handler()

    /**
     * delays contstants
     */
    private val QUICKLY_RUNNING_QUATER_SEC = 250
    private val HALF_MINUTE_MILI_SEC = 3000

    /**
     * that property responsible for the delay
     */
    private val delay : Int = HALF_MINUTE_MILI_SEC

    /**
     * creates ui of the screen which in activity_main
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // attach alarm and text feedback to event handler class

        eventHandler.attachManyObservers(Alarm(eventHandler), TextFeedbackClass(eventHandler,this),
            EmergencySignal(eventHandler));

        // makes a connection with certain server with socket.io
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
    }

    /**
     * sending the location each of x seconds
     */
    override fun onResume() {
        handler.postDelayed(object : Runnable {
            override fun run() {

                var wifiManager = applicationContext.getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
                var wifiInfo = wifiManager!!.getConnectionInfo()
                val singleton : InformationClass = InformationClass.instance
                wifiManager.startScan()
                val wifiScanReceiver = object : BroadcastReceiver() {

                    override fun onReceive(context: Context, intent: Intent) {
                        val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                        if (success) {
                            scanSuccess(wifiManager)
                        } else {
                            scanFailure(wifiManager)
                        }
                    }
                }
                var accessPoint : AccessPoint = AccessPoint()
                //Log.d("first acsses point", wifiManager.scanResults.get(0).frequency.toString())
                //Log.d("secon acsses point", wifiManager.scanResults.get(1).frequency.toString())
                var rssi : Int = wifiInfo.rssi
                var ip : Int = wifiInfo.ipAddress
                var mac : String = wifiInfo.macAddress
                var id : Int = wifiInfo.networkId
                var freq : Int = wifiInfo.getFrequency()
                accessPoint.setRssi(rssi)
                accessPoint.setIpAddress(ip)
                accessPoint.setMac(mac)
                accessPoint.setFrequency(freq)
                singleton.addAccessPoint(accessPoint)
                Log.v("singleton", singleton.toString())
                val mSocket = SocketHandler.getSocket()
                mSocket.emit("wifiInformation", singleton)
                handler.postDelayed(this, Integer.toUnsignedLong(delay))
            }
        }, Integer.toUnsignedLong(delay))
        super.onResume()
    }

    /**
     * notifies all observers of eventHandler
     * @param view - a view button
     * @notice we must view parameter because this is how it defines
     *         in activity_main.xml with the view/button
     */
    fun notifyObservers(view : View) {
        eventHandler.notifyALlObservers(applicationContext)
    }

    private fun scanSuccess(wifiManager: WifiManager) {
        val results = wifiManager.scanResults
        Log.d("scans",results.get(0).frequency.toString())

    }

    private fun scanFailure(wifiManager: WifiManager) {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d("scans failed",results.toString())
    }
}