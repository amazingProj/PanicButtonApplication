package com.example.custombutton.main.model

import android.content.Context
import android.media.MediaPlayer
import com.example.custombutton.R

@Suppress("DEPRECATION")
class Alarm : Observer{
    private var media: MediaPlayer? = MediaPlayer()

    constructor(eventHandler: EventHandler){
        eventHandler.attachObserver(this)
    }
    override fun update(context : Context) {
        media = MediaPlayer.create(context, R.raw.emergency_sound)
        media?.start()
    }
}