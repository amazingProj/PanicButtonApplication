package com.example.custombutton.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.custombutton.R
import com.example.custombutton.main.model.Alarm
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.model.Observer
import com.example.custombutton.main.model.TextFeedbackClass


/**
 * class represents the main activity includes emergency button
 */

class MainActivity : AppCompatActivity() {
    // shared preference event handler
   private var eventHandler : EventHandler = EventHandler()

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

    /**
     * notifies all observers of eventHandler
     */
    fun notifyObservers(view: View) {
        for (observer : Observer in eventHandler?.getObservers()?.toList()!!){
            observer.update(applicationContext)
        }
    }
}