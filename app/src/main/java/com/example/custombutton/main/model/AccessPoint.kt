package com.example.custombutton.main.model

class AccessPoint {
    private var SSID : String? = null
    private var BSSID : String? = null
    private var humanReadableRssi : Int? = null
    private var frequency : Int? = null
    private var rssi : Int? = null


    fun setRssi(_rssi : Int){
        rssi = _rssi
    }

    fun setSsid(_ssid : String) {
        SSID = _ssid
    }

    fun setBssid(_bssid : String){
        BSSID = _bssid
    }

    fun setHumanReadableRssi(_humanReadableRssi : Int){
        humanReadableRssi = _humanReadableRssi
    }

    fun setFrequency(_frequency : Int){
        frequency = _frequency
    }

    override fun toString(): String {
        return "Rssi is $rssi \nssid is $SSID\n" +
                "\nbssid id is $BSSID\n freq is $frequency"

    }

}