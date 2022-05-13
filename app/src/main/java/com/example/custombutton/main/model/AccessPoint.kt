package com.example.custombutton.main.model

import com.google.gson.Gson

/**
 * class represents essential values which were gotten from access point device
 */
class AccessPoint {

    private var BSSID : String? = null
    var rssi : Int? = null

    private val gson = Gson()
/*************** Setters ***************/

    /**
     * setter of rssi
     * @param _rssi - an int of recieved signal stgrenth indicator
     */
    fun setRssi(_rssi : Int){
        rssi = _rssi
    }

    /**
     * setter of bssid
     * @param _bssid - a string of bssid of access point
     */
    fun setBssid(_bssid : String){
        BSSID = _bssid
    }

    /*************** Function ***************/

    /**
     * makes its value to a dictionary
     * @return a string of a dictionary
     */
    fun toDictionry() : String {
        val dict = mapOf(
            "Rssi" to rssi,
            "Bssid" to BSSID,
        )
        return gson.toJson(dict).toString()
    }

    override fun toString(): String {
        return "Rssi is $rssi \n" +
                "\nbssid id is $BSSID\n"

    }

}