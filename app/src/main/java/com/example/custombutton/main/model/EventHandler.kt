package com.example.custombutton.main.model

import com.example.custombutton.main.model.Observer

/**
 * represents event handler of all his observers
 */
class EventHandler{
    private var observers : MutableList<Observer>? = null;

    fun EventHandler() {
    }

    fun attachObserver(_observer : Observer){
        observers?.add(_observer)
    }

    fun notifyALlObservers(){
        for(observer : Observer in observers!!){
            observer.update()
        }
    }
}