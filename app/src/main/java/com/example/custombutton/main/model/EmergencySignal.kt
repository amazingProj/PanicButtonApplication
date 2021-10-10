package  com.example.custombutton.main.model

import android.content.Context

class EmergencySignal : Observer {
    constructor(eventHandler: EventHandler){
        eventHandler.attachObserver(this)
    }
    override fun update(context : Context) {
    }
}