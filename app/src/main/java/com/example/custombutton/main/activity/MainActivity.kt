package com.example.custombutton.main.activity
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
import android.net.wifi.WifiInfo
import android.R.attr.delay
import android.widget.Toast

/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {
    private var eventHandler : EventHandler = EventHandler()
    private var handler : Handler = Handler()
    private val delay : Int = 500

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
        handler.postDelayed({
            var wifiManager = this.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
            var wifiInfo = wifiManager!!.getConnectionInfo()
            var bssid = wifiInfo.bssid
            var rssi = wifiInfo.rssi
            var view : TextView = findViewById(R.id.textView) as TextView
            view.text = "RSSI $rssi"
            view = findViewById(R.id.textView2) as TextView
            view.text = "BSSID $bssid"
        }, Integer.toUnsignedLong(delay))
        super.onResume()
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