package com.example.custombutton.main.model

class AccessPoint {
    private var ipAddress : Int? = null
    private var mac : String? = null
    private var networkId : Int? = null
    private var humanReadableRssi : Int? = null
    private var frequency : Int? = null
    private var rssi : Int? = null


    fun setRssi(_rssi : Int){
        rssi = _rssi
    }

    fun setIpAddress(_ip : Int) {
        ipAddress = _ip
    }

    fun setMac(_mac : String){
        mac = _mac
    }

    fun setNetworkId(_networkId : Int){
        networkId = _networkId
    }

    fun setHumanReadableRssi(_humanReadableRssi : Int){
        humanReadableRssi = _humanReadableRssi
    }

    fun setFrequency(_frequency : Int){
        frequency = _frequency
    }

    override fun toString(): String {
        return "Rssi is $rssi \nip is $ipAddress\n" +
                "mac is $mac\nnetwork id is $networkId\n freq is $frequency"

    }

}