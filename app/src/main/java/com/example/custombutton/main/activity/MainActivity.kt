package com.example.custombutton.main.activity

import com.example.custombutton.main.service.RetrofitPost
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call

/**
 * class represents the main activity includes emergency button
 */
class MainActivity : AppCompatActivity() {
    private var retrofit : Retrofit? = null
    private var retrofitPost : RetrofitPost? = null
    private val BASE_URL : String = "http://10.0.2.2:3000"
    private var eventHandler : EventHandler = EventHandler()
    private var handler : Handler = Handler()
    private val delay : Int = 3000
    /**
     * creates ui of the screen which in activity_main
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        retrofitPost = retrofit?.create(RetrofitPost::class.java)

        // attach alarm and text feedback to event handler class
        if (eventHandler.getObservers().isNullOrEmpty()){
            eventHandler.attachObserver(Alarm(eventHandler))
            eventHandler.attachObserver(TextFeedbackClass(eventHandler))
        }
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
                var bssid = wifiInfo.bssid
                var rssi = wifiInfo.rssi
                var ip : Int = wifiInfo.ipAddress
                var view : TextView = findViewById(R.id.textView) as TextView
                view.text = "RSSI $rssi"
                view = findViewById(R.id.textView2) as TextView
                view.text = "IP $ip"
                singleton.setRssi(rssi)
                singleton.setIpAddress(ip)
                retrofitPost!!.executesWifiInfo(singleton)
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
}