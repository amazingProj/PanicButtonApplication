package com.example.custombutton.main.model

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class InformationClass {
    @SerializedName("mac")
    @Expose
    private var macAddress : String = String()

    @SerializedName("ssid")
    @Expose
    private var ssid : String = String()

    @SerializedName("rssi")
    @Expose
    private var rssi : Int? = null

    @SerializedName("ip")
    @Expose
    private var ipAddress : Int? = null

    @SerializedName("alarm")
    @Expose
    private var isAlarmed : Boolean? = null

    init {
        isAlarmed = false
    }
    /**
     * singleton objects whose properties
     * and functions are tied to a class but not to the instance of that class
     * @see https://medium.com/swlh/kotlin-basics-of-companion-objects-a8422c96779b
     */
    companion object {
        val instance = InformationClass()
    }

    fun setRssi(_rssi : Int){
        rssi = _rssi
    }

    fun setIpAddress(_ip : Int){
        ipAddress = _ip
    }

    fun setIsAlarmed(_isAlarmed : Boolean){
        isAlarmed = _isAlarmed
    }

    fun setMac(_mac : String){
        macAddress = _mac
    }

    fun setSsid(_ssid : String){
        ssid = _ssid
    }
}