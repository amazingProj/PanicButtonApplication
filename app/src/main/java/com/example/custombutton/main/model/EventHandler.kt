package com.example.custombutton.main.model

import android.content.Context
import android.view.View
import com.example.custombutton.main.ui.TextFeedbackClass

/**
 * represents event handler of all his observers
 *
 * this class handle the calls when the emergency button is pressed
 */
class EventHandler
/**
 * default constructor of event handler
 */
{
    /********** field **********/
    private var observers : MutableList<Observer> = ArrayList()

    /********** functions ***********/

    /**
     * attaches a new observer
     * @param _observer - a new observer related this handler
     */
    fun attachObserver(_observer : Observer){
        observers.add(_observer)
    }

    /**
     * adds many observers to event handler
     * @param _observers - many classes that implement the observer interface
     */
    fun attachManyObservers(vararg  _observers : Observer){
        for (_observer in _observers){
            attachObserver(_observer)
        }
    }

    fun removeObserver(_observer: Observer){
        observers.remove(_observer)
    }

    /**
     * notifies all observers
     * @param context - the activity within we do that notify
     */
    fun notifyALlObservers(context : Context){
        for(observer : Observer in observers){
            observer.update(context)
        }
    }

    /**
     * notify all observer turn off the alarm
     * when the alarm is off this will be called
     */
    fun notifyAllObserverEmergencySignal(){
        for(observer : Observer in observers){
            observer.emergencySignalHasSent()
        }
    }
}