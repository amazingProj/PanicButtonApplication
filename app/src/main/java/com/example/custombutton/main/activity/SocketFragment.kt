package com.example.custombutton.main.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.custombutton.R
import com.example.custombutton.main.model.InformationClass
import com.example.custombutton.main.service.SocketHandler
import com.example.custombutton.main.service.SocketSender
import com.example.custombutton.main.activity.MainActivity

class SocketFragment : Fragment() {
    private val INVISIBLE = 0.0f
    private val VISIBLE = 1.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.socket_fragment, container, false)

        val button : Button = view.findViewById(R.id.back_button)
        button.setOnClickListener {
           startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }

        val notice : TextView = view.findViewById(R.id.notice)
        //
        notice.alpha = INVISIBLE
        val editIPAddress : EditText = view.findViewById(R.id.editTextTextIPAddress)
        editIPAddress.hint = SocketHandler.getIpAddress()
        editIPAddress.setRawInputType(Configuration.KEYBOARD_12KEY);
        editIPAddress.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                Log.d("editable", s.toString())
                val pattern = Regex("\\d+(\\.\\d+(\\.\\d+(\\.\\d+)))")

                val condition : Boolean = s.toString().matches(pattern)

                if (condition){
                    notice.text = getString(R.string.all_fine_hebrew)
                    notice.setTextColor(resources.getColor(R.color.green))
                    notice.alpha = VISIBLE
                    SocketHandler.setIpAddress(s.toString())
                    SocketSender.newServerConnection()
                }
                else {
                    notice.text = getString(R.string.not_all_fine)
                    notice.setTextColor(resources.getColor(R.color.red))
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
        return view
    }
}