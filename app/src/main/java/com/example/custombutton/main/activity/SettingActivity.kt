package com.example.custombutton.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.custombutton.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_layout)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, HomeFragment()).commit()
        var navigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId){
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, HomeFragment()).commit()
                }
                R.id.socketio -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, SocketFragment()).commit()
                }
                R.id.mqtt -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, MqttFragment()).commit()
                }
            }
            true
        }
    /*
        val notice : TextView = findViewById(R.id.notice)
        //
        notice.alpha = INVISIBLE
        val editIPAddress : EditText = findViewById(R.id.editTextTextIPAddress)
        editIPAddress.hint = SocketHandler.getIpAddress()
        editIPAddress.setRawInputType(Configuration.KEYBOARD_12KEY);
        editIPAddress.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("editable", s.toString())
                val pattern = Regex("\\d+(\\.\\d+(\\.\\d+(\\.\\d+)))")

                val condition : Boolean = s.toString().matches(pattern)

                if (condition){
                    notice.text = getString(R.string.all_fine_hebrew)
                    notice.setTextColor(getColor(R.color.green))
                    notice.alpha = VISIBLE
                    SocketHandler.setIpAddress(s.toString())
                    SocketSender.newServerConnection()
                }
                else {
                    notice.text = getString(R.string.not_all_fine)
                    notice.setTextColor(getColor(R.color.red))
                    notice.alpha = VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

            }
        })
        val singleton : InformationClass = InformationClass.instance
        val editSpecialId : EditText = findViewById(R.id.editSpecialId)
        editSpecialId.hint = singleton.getSpecialId().toString()
        editSpecialId.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val temp = s.toString().toIntOrNull()
                if (temp != null){
                    editSpecialId.hint = singleton.getSpecialId().toString()
                    singleton.setNewId(temp)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

            }
        })
        val offOrOn : TextView = findViewById(R.id.onOrOff)

         */
    }
}