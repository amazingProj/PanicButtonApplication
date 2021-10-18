package com.example.custombutton.main.service

import com.example.custombutton.main.model.InformationClass
import java.util.HashMap
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * posts to the internet this body using retrofit2
 */
interface RetrofitPost {
    /**
     * posts wifi information of this device to the internet
     */
    @POST("/wifiInfo")
    open fun executesWifiInfo(@Body  informationClass: InformationClass): Call<Void?>?
}