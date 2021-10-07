package com.example.custombutton.ui

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.custombutton.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var media: MediaPlayer? = null
    private val soundId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        media = MediaPlayer.create(this, R.raw.emergency_sound)
    }

    fun playSound(view: View) {
        media?.start()
       Toast.makeText(applicationContext,"הפעלת את אזעקת המצוקה" ,Toast.LENGTH_SHORT).show()
    }
}