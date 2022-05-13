package com.example.custombutton.main.model

import com.google.gson.Gson

/**
 * class represents the information that was collected from
 * actual wifi access points sscan with the android device
 */
class InformationClass {
    // boolean variable tells us if the alarm is on or off
    private var isAlarmed : Boolean? = null
    private var batteryLevelField : Float? = null
    //private var macAdderess : String = String()
    // a list off all access points in a scan
    private var accessPoints : ArrayList<AccessPoint>? = ArrayList()
    private var IdNumber : String = "AssafAndroid1010"

    val gson = Gson()

    init {
        isAlarmed = false
        //macAdderess = DeviceInfo.getMacAddress()
    }
    /**
     * singleton objects whose properties
     * and functions are tied to a class but not to the instance of that class
     * @see https://medium.com/swlh/kotlin-basics-of-companion-objects-a8422c96779b
     */
    companion object {
        val instance = InformationClass()
    }

    /**
     * setter of isAlarmed
     * @param _isAlarmed - a boolean variable of alarmed off
     */
    fun setIsAlarmed(_isAlarmed : Boolean){
        isAlarmed = _isAlarmed
    }

    fun setBattery(_battery : Float){
        batteryLevelField = _battery
    }


    /**
     * setter of accessPoint
     * @param accessPoint - an access point class
     */
    fun addAccessPoint(accessPoint: AccessPoint){
        accessPoints?.add(accessPoint)
    }

    fun getNumberOfAccessPoint() : Int? {
        return accessPoints!!.size
    }

    /**
     * formatting all the information of a scan of wifi access points
     */
    fun toSend() : String{
        var counter = 1
        var result = HashMap<String, Any>()
        val string = "AccessPoint"
        var accessPointNumber = string + counter.toString()

        for (accessPoint in accessPoints!!){
            result[accessPointNumber] = accessPoint.toDictionry()
            ++counter
            accessPointNumber = string + counter.toString()
        }
        result["Battery"] = batteryLevelField.toString()
        result["isAlarmedOn"] = isAlarmed.toString()
        result["NumberOfAccessPoints"] = accessPoints!!.size.toString()
        //result["deviceMacAddress"] = macAdderess
        result["specialIdNumber"] = IdNumber
        return gson.toJson(result).toString()
    }

    @Override
    override fun toString(): String {
        var s = String()
        for (accessPoint in accessPoints!!){
            s += accessPoint.toString() + "\n\n"
        }
        return s
    }
}