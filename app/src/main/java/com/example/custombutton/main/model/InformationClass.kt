package com.example.custombutton.main.model

class InformationClass (){
    private var rssi : Int? = null
    private var ipAddress : Int? = null
    private var isAlarmed : Boolean? = null

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
}