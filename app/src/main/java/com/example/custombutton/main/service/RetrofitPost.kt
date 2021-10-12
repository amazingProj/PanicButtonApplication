package com.example.custombutton.main.service

import java.util.HashMap
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitPost {
    @POST("/wifiInfo")
    open fun executesSignup(@Body map: HashMap<Int?, Int?>?): Call<Void?>?
}