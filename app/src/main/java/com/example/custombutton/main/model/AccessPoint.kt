package com.example.custombutton.main.model

import com.google.gson.Gson

/**
 * class represents essential values which were gotten from access point device
 */
class AccessPoint {
    /**
     * access point values
     */
    private var SSID : String? = null
    private var BSSID : String? = null
    private var frequency : Int? = null
    private var rssi : Int? = null

    val gson = Gson()
/*************** Setters ***************/

    /**
     * setter of rssi
     * @param _rssi - an int of recieved signal stgrenth indicator
     */
    fun setRssi(_rssi : Int){
        rssi = _rssi
    }

    /**
     * setter of ssid
     * @param _ssid - a string of ssid of access point
     */
    fun setSsid(_ssid : String) {
        SSID = _ssid
    }

    /**
     * setter of bssid
     * @param _bssid - a string of bssid of access point
     */
    fun setBssid(_bssid : String){
        BSSID = _bssid
    }

    /**
     * setter of frequency
     * @param _frequency - an Int represents rssi frequency
     */
    fun setFrequency(_frequency : Int){
        frequency = _frequency
    }

    /*************** Function ***************/

    /**
     * makes its value to a dictionary
     * @return a string of a dictionary
     */
    fun toDictionry() : String {
        val dict = mapOf(
            "Rssi" to rssi,
            "Ssid" to SSID,
            "Bssid" to BSSID,
            "frequency" to frequency
        )
        return gson.toJson(dict).toString()
    }

    override fun toString(): String {
        return "Rssi is $rssi \nssid is $SSID\n" +
                "\nbssid id is $BSSID\n freq is $frequency"

    }

}