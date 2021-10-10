package com.example.custombutton.main.model

import android.content.Context

/**
 * represents event handler of all his observers
 */
class EventHandler
/**
 * default constructor of event handler
 */
{
    /********** field **********/

    private var observers : MutableList<Observer> = ArrayList()

    /********** getter **********/

    /**
     * getter of mutable list
     * @return a mutable list of observers
     */
    fun getObservers(): MutableList<Observer>? {
        return observers
    }

    /********** functions ***********/

    /**
     * attaches a new observer
     * @param _observer - a new observer related this handler
     */
    fun attachObserver(_observer : Observer){
        observers.add(_observer)
    }

    /**
     * notifies all observers
     * @param context - the activity within we do that notify
     */
    fun notifyALlObservers(context : Context){
        for(observer : Observer in observers!!){
            observer.update(context)
        }
    }
}