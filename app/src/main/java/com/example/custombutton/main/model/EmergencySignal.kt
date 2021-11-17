package  com.example.custombutton.main.model

import android.content.Context

/**
 * class represents an emergency signal sender to a server
 */
class EmergencySignal
/**
 * constructs an emergency signal sender class by an event handler
 * which it relates to it
 * @param eventHandler - a related event handler class
 */(eventHandler: EventHandler) : Observer {
    init {
        eventHandler.attachObserver(this)
    }
    @Override
    override fun update(context : Context) {
        val singleton : InformationClass = InformationClass.instance
        singleton.setIsAlarmed(true)
    }

    override fun emergencySignalHasSent() {
        TODO("Not yet implemented")
    }
}