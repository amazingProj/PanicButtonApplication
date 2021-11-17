package com.example.custombutton.main.model

import android.content.Context
import android.media.MediaPlayer
import com.example.custombutton.R

/**
 * class represents an alarm sound in the background
 */
@Suppress("DEPRECATION")
class Alarm
/**
 * constructs an alarm class by an event handler which it relates to it
 * @param eventHandler - a related event handler class
 */(eventHandler: EventHandler) : Observer{
    private var isAlarmOn : Boolean? = null
    private var media: MediaPlayer? = MediaPlayer()

    init {
        eventHandler.attachObserver(this)
        isAlarmOn = false
    }

    @Override
    override fun update(context : Context) {
        if (isAlarmOn == false) {
            isAlarmOn = true
            media = MediaPlayer.create(context, R.raw.emergency_sound)
            media?.isLooping = true
            media?.start()
        }
    }

    override fun emergencySignalHasSent() {
        TODO("Not yet implemented")
    }
}