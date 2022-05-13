package com.example.custombutton.main.model

import android.content.Context
import android.media.MediaPlayer
import com.example.custombutton.R
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient

/**
 * class represents an alarm sound in the background
 */
@Suppress("DEPRECATION")
class Alarm
/**
 * constructs an alarm class by an event handler which it relates to it
 * @param eventHandler - a related event handler class
 */(eventHandler: EventHandler) : Observer{
    // when it is alarmed make this true otherwise false
    private var isAlarmOn : Boolean? = null

    // media player rings the alarm to better locate the person
    private var media: MediaPlayer? = MediaPlayer()

    init {
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

    @Override
    override fun emergencySignalHasSent() {
        isAlarmOn = false
        media?.stop()
    }
}