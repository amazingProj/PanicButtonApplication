package com.example.custombutton.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.example.custombutton.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.home_fragment, container, false)
        val backButton : Button = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
        return view
    }
}