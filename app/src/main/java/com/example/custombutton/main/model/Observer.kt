package com.example.custombutton.main.model

import android.content.Context

/**
 * represents observer
 * when the user click the button we use observer pattern
 * to update all related function in application
 */
interface Observer {
    /**
     * updates observer
     * @param context - the activity we are doing the notifications
     */
    fun update(context : Context)

    fun emergencySignalHasSent()
}