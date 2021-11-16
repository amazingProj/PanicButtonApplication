package com.example.custombutton.main.model

class InformationClass {
    private var isAlarmed : Boolean? = null
    private var accessPoints : ArrayList<AccessPoint>? = ArrayList<AccessPoint>()

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

    fun setIsAlarmed(_isAlarmed : Boolean){
        isAlarmed = _isAlarmed
    }

    fun addAccessPoint(accessPoint: AccessPoint){
        accessPoints?.add(accessPoint)
    }

    override fun toString(): String {
        var s = String()
        for (accessPoint in accessPoints!!){
            s += accessPoint.toString() + "\n\n"
        }
        return s
    }
}