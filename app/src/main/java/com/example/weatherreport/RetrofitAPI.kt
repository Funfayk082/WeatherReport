package com.example.weatherreport

import com.example.weatherreport.models.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {
    @GET("forecast")

    fun  getWeather(@Query("latitude") latitude: Double,
                    @Query("longitude") longitude: Double,
                    @Query("current") current: String,
                    @Query("hourly") hourly: String): Call<Response?>?
}

//