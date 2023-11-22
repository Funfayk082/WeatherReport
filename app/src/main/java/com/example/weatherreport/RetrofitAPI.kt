package com.example.weatherreport

import com.example.weatherreport.models.Response
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("forecast?latitude=57.75&longitude=40.9375&current=temperature_2m,weathercode&hourly=temperature_2m,weathercode")
    fun  getWeather(): Call<Response?>?
}

//