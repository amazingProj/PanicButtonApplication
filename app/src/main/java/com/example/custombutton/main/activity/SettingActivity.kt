package com.example.custombutton.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.custombutton.R
import com.example.custombutton.main.service.SocketHandler
import com.example.custombutton.main.service.SocketSender

class SettingActivity : AppCompatActivity() {
    private val INVISIBLE = 0.0f
    private val VISIBLE = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val notice : TextView = findViewById(R.id.notice)
        //
        notice.alpha = INVISIBLE
        val editIPAddress : EditText = findViewById(R.id.editTextTextIPAddress)
        editIPAddress.hint = SocketHandler.getBaseUrl()
        editIPAddress.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("editable", s.toString())
                val pattern = Regex("\\d+(\\.\\d+(\\.\\d+(\\.\\d+)))")

                var condition : Boolean = s.toString().matches(pattern)

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
                /*notice.setTextColor(getColor(R.color.black))
                notice.text = getString(R.string.format)
                notice.alpha = VISIBLE*/
            }
        })
    }



}