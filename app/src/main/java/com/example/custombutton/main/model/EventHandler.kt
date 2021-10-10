package com.example.custombutton.main.model

import android.content.Context

/**
 * represents event handler of all his observers
 */
class EventHandler{
    private var observers : MutableList<Observer> = ArrayList();

    constructor() {
    }

    fun attachObserver(_observer : Observer){
        observers?.add(_observer)
    }

    fun notifyALlObservers(context : Context){
        for(observer : Observer in observers!!){
            observer.update(context)
        }
    }

    fun getObservers(): MutableList<Observer>? {
        return observers;
    }
}