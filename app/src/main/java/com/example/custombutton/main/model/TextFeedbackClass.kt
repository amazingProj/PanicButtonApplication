package com.example.custombutton.main.model

import android.content.Context
import android.widget.Toast

class TextFeedbackClass : Observer {
    constructor(eventHandler: EventHandler){
        eventHandler.attachObserver(this)
    }
    override fun update(context : Context) {
        Toast.makeText(context,"הפעלת את אזעקת המצוקה" , Toast.LENGTH_SHORT).show()
    }
}