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
    private var media: MediaPlayer? = MediaPlayer()

    init {
        eventHandler.attachObserver(this)
    }

    @Override
    override fun update(context : Context) {
        media = MediaPlayer.create(context, R.raw.emergency_sound)
        media?.start()
    }
}