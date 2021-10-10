package com.example.custombutton.main.ui

import android.content.Context
import android.widget.Toast
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.model.Observer

/**
 * class represents class that gives feedback with text
 */
class TextFeedbackClass
/**
 * constructs text feedback class
 * @param eventHandler - an event handler class which handles this
 */(eventHandler: EventHandler) : Observer {
    init {
        eventHandler.attachObserver(this)
    }

    /**
     * updates all observers
     * @param context - an activity we show the toast in it
     * @see Observer.update()
     */
    @Override
    override fun update(context : Context) {
        Toast.makeText(context,"הפעלת את אזעקת המצוקה" , Toast.LENGTH_SHORT).show()
    }
}