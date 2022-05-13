package com.example.custombutton.main.ui

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.custombutton.R
import com.example.custombutton.main.model.EventHandler
import com.example.custombutton.main.model.Observer
import android.view.LayoutInflater





/**
 * class represents class that gives feedback with text
 */
class TextFeedbackClass
/**
 * constructs text feedback class
 * @param eventHandler - an event handler class which handles this
 */(eventHandler: EventHandler, activity : Activity) : Observer {
    /**
     * property for only one time per event to know if he already push the button
     */
    private var isAlarmOn : Boolean? = null

    /**
     * the activity in which we show the toast message
     */
    private var activityView : Activity? = null

    val layout = activityView?.layoutInflater?.inflate (
        R.layout.custom_toast_message,
        activityView?.findViewById(R.id.custom_button_layout)
    )

    /**
     * initial constructor of this class
     */
    init {
        isAlarmOn = false
        activityView = activity
    }

    /**
     * updates all observers
     * @param context - an activity we show the toast in it
     * @see Observer.update()
     */
    @Override
    override fun update(context : Context) {
        var toast: Toast
        val li = LayoutInflater.from(context)
        if (isAlarmOn == true){
            toast = Toast.makeText(li.inflate(R.layout.custom_toast_message, null,false).context,"המטפלים בדרך אליך" , Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            toast = Toast.makeText(li.inflate(R.layout.custom_toast_message, null,false).context,"הפעלת את אזעקת המצוקה" , Toast.LENGTH_LONG)
            toast.show()
            isAlarmOn = true
        }
    }

    @Override
    override fun emergencySignalHasSent() {
        isAlarmOn = false
    }
}