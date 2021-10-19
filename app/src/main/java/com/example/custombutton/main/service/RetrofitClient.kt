package com.example.custombutton.main.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient{
    private var retrofit: Retrofit? = null
    private val baseUrl : String = "http://10.0.2.2:3000/"

    init{
        retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getClient() : Retrofit? {
        return  retrofit
    }
}