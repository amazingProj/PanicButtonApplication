package com.example.custombutton.main.ui

import android.content.Context
import android.widget.Toast
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.model.Observer

class TextFeedbackClass : Observer {
    constructor(eventHandler: EventHandler){
        eventHandler.attachObserver(this)
    }
    override fun update(context : Context) {
        Toast.makeText(context,"הפעלת את אזעקת המצוקה" , Toast.LENGTH_SHORT).show()
    }
}