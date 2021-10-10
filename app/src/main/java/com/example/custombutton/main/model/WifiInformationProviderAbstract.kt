package com.example.custombutton.main.model

import android.net.wifi.WifiInfo

abstract class WifiInformationProviderAbstract {
    abstract fun getRssi() : Int
    abstract fun getBssid() : String
    abstract fun getWifiInfo() : WifiInfo
}