package com.example.custombutton.main.service

import java.util.HashMap
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitPost {
    @POST("/wifiInfo")
    open fun executesWifiInfo(@Body map: HashMap<String?, Int?>?): Call<Void?>?
}