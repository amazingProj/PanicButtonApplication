package com.example.custombutton.main.model

import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

/**
 * class represents Wifi information provider
 */
class WifiInformationProvider {
    private var wifiManager : WifiManager? = null
    private var wifiInformation : WifiInfo? = null

    fun setWifiManager(_wifiManager: WifiManager){
        wifiManager = _wifiManager
    }

    fun getWifiManger() : WifiManager? {
        return wifiManager
    }

    fun setWifiInformation(_wifiInfo: WifiInfo){
        wifiInformation = _wifiInfo
    }

    fun getWifiInformation() : WifiInfo?{
        return wifiInformation
    }
}