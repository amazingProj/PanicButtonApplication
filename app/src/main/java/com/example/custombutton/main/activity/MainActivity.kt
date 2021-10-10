package com.example.custombutton.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.custombutton.R


/**
 * class represents the main activity includes emergency button
 */
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var media: MediaPlayer? = null

    /**
     * creates ui of the screen which in activity_main
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playSound(view: View) {
        media = MediaPlayer.create(this, R.raw.emergency_sound)
        media?.start()
       Toast.makeText(applicationContext,"הפעלת את אזעקת המצוקה" ,Toast.LENGTH_SHORT).show()
    }
}