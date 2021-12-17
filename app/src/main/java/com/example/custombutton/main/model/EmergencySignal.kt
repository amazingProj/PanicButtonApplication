package  com.example.custombutton.main.model

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Handler
import android.util.Log
import com.example.custombutton.main.service.SocketSender

/**
 * class represents an emergency signal sender to a server
 */
class EmergencySignal
/**
 * constructs an emergency signal sender class by an event handler
 * which it relates to it
 * @param eventHandler - a related event handler class
 */(eventHandler: EventHandler) : Observer {
    private var isAlarmOn : Boolean = false
    // the route path of the sent information
    private val routePathWifiAccessPoint = "panic-signal"

    // in miliseconds for delaying some tasks
    private val TWO_SECONDS = 2000
    private val TWO_MINUTES_DELAY = 120000

    /**
     * handler class responsible for to do certain task every x seconds
     */
    private var handler: Handler = Handler()


    private var newEventHandler: EventHandler? = null

    init {
        eventHandler.attachObserver(this)
        newEventHandler = eventHandler
    }

    @Synchronized
    @Override
    override fun update(context: Context) {
        if (!isAlarmOn){
            val info = InformationClass.instance
            info.setIsAlarmed(true)

            var wifiManager = context.getSystemService(android.content.Context.WIFI_SERVICE) as WifiManager?
            var wifiInfo = wifiManager!!.getConnectionInfo()
            var bool : Boolean = wifiManager.startScan()

            // adding into singleton the access points that were found
            if (bool){
                wifiManager.scanResults.forEach{
                    var accessPoint : AccessPoint = AccessPoint()
                    accessPoint.setFrequency(it.frequency)
                    accessPoint.setBssid(it.BSSID)
                    accessPoint.setSsid(it.SSID)
                    accessPoint.setRssi(it.level)
                    info.addAccessPoint(accessPoint)
                }
            }

            SocketSender.sendDataToServer(
                routePathWifiAccessPoint,
                info.toSend()
            );

            info.newAccessPoints()

            isAlarmOn = true

            // when it succeeded then do that
            handler.postDelayed({
                newEventHandler?.notifyAllObserverEmergencySignal()
            }, TWO_MINUTES_DELAY.toLong())
        }
    }

    @Synchronized
    @Override
    override fun emergencySignalHasSent() {
        val singleton: InformationClass = InformationClass.instance
        singleton.setIsAlarmed(false)
        isAlarmOn = false
    }
}