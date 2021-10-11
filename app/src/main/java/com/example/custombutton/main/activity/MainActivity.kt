package com.example.custombutton.main.activity

import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.custombutton.R
import com.example.custombutton.main.model.Alarm
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.ui.TextFeedbackClass
import android.net.wifi.WifiInfo


/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {
    private var eventHandler : EventHandler = EventHandler()


    /**
     * creates ui of the screen which in activity_main
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // attach alarm and text feedback to event handler class
        if (eventHandler.getObservers().isNullOrEmpty()){
            eventHandler.attachObserver(Alarm(eventHandler))
            eventHandler.attachObserver(TextFeedbackClass(eventHandler))
        }
    }

    override fun onResume() {
        super.onResume()
        var wifiManager = this.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
        var wifiInfo = wifiManager!!.getConnectionInfo()
        var bssid = wifiInfo.bssid
        var rssi = wifiInfo.rssi
    }

    /**
     * notifies all observers of eventHandler
     * @param view - a view button
     * @notice we must view parameter because this is how it defines
     *         in activity_main.xml with the view/button
     */
    fun notifyObservers(view: View) {
        eventHandler.notifyALlObservers(applicationContext)
    }
}