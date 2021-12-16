package com.example.custombutton.main.model

/**
 * class represents the information that was collected from
 * actual wifi access points sscan with the android device
 */
class InformationClass {
    // boolean variable tells us if the alarm is on or off
    private var isAlarmed : Boolean? = null

    // a list off all access points in a scan
    private var accessPoints : ArrayList<AccessPoint>? = ArrayList()

    // mac address of the phone
    private var macPhone : String? = null

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

    /**
     * setter of isAlarmed
     * @param _isAlarmed - a boolean variable of alarmed off
     */
    fun setIsAlarmed(_isAlarmed : Boolean){
        isAlarmed = _isAlarmed
    }

    /**
     * setter of accessPoint
     * @param accessPoint - an access point class
     */
    fun addAccessPoint(accessPoint: AccessPoint){
        accessPoints?.add(accessPoint)
    }

    /**
     * setter of mac address
     * @param _mac - a string of mac address
     */
    fun setMac(_mac : String){
        macPhone = _mac
    }

    /**
     * clears all the access points from the singleton
     */
    fun newAccessPoints(){
        accessPoints?.clear()
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
        result["isAlarmedOn"] = isAlarmed.toString()
        result["macPhone"] = macPhone.toString()
        result["NumberOfAccessPoints"] = accessPoints!!.size.toString()
        return result.toString()
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