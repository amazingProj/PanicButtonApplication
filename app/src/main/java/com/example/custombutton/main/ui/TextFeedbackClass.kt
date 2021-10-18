package com.example.custombutton.main.ui

import android.content.Context
import android.widget.Toast
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.model.Observer
import kotlinx.coroutines.delay

/**
 * class represents class that gives feedback with text
 */
class TextFeedbackClass
/**
 * constructs text feedback class
 * @param eventHandler - an event handler class which handles this
 */(eventHandler: EventHandler) : Observer {
    private var isAlarmOn : Boolean? = null
    init {
        eventHandler.attachObserver(this)
        isAlarmOn = false
    }

    /**
     * updates all observers
     * @param context - an activity we show the toast in it
     * @see Observer.update()
     */
    @Override
    override fun update(context : Context) {
        var toast: Toast?
        if (isAlarmOn == true){
            toast = Toast.makeText(context,"המטפלים בדרך אליך" , Toast.LENGTH_LONG)
            toast.show()
        }
        else{
            toast = Toast.makeText(context,"הפעלת את אזעקת המצוקה" , Toast.LENGTH_SHORT)
            toast.show()
            isAlarmOn = true
        }
    }
}