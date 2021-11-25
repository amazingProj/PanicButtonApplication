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
    // the route path of the sent information
    private val routePathWifiAccessPoint = "wifiInformation"

    // in miliseconds for delaying some tasks
    private val TWO_SECONDS = 2000
    private val TWO_MINUTES_DELAY = 1200000

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
        val singleton: InformationClass = InformationClass.instance
        singleton.setIsAlarmed(true)
        var isSentSucceded = false

        /*
        while (isSentSucceded) {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    isSentSucceded = SocketSender.sendDataToServer(
                        routePathWifiAccessPoint,
                        singleton.toString()
                    );
                }
            }, Integer.toUnsignedLong(TWO_SECONDS))
        }
        */
        // when it succeeded then do that
        handler.postDelayed({
            newEventHandler?.notifyAllObserverEmergencySignal()
        }, TWO_MINUTES_DELAY.toLong())
    }

    @Synchronized
    @Override
    override fun emergencySignalHasSent() {
        val singleton: InformationClass = InformationClass.instance
        singleton.setIsAlarmed(false)
    }
}