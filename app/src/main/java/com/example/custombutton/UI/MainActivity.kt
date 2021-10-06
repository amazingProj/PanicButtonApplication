package com.example.custombutton.UI

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.custombutton.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private var soundPool: SoundPool? = null
    private val soundId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.button)
        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(baseContext, R.raw.alarm, 1)
    }

    fun playSound(view: View) {
        soundPool?.play(soundId, 1F, 1F, 0, 0, 1F)
        Toast.makeText(applicationContext,"הפעלת את אזעקת המצוקה" ,Toast.LENGTH_SHORT).show()
    }
}