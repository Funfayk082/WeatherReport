package com.example.weatherreport.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherreport.RetrofitAPI
import com.example.weatherreport.WeatherService
import com.example.weatherreport.models.Response
import retrofit2.Retrofit
import java.lang.reflect.Array.get

class WeatherRepository {
    val weatherService = WeatherService()
    suspend fun getData(lon: Double, lat: Double): MutableLiveData<Response> {
        var mutableLiveData = MutableLiveData<Response>()
        //mutableLiveData.value = weatherService.fetchWeatherByCityName(lon, lat)
        //var retrofitAPI: RetrofitAPI
        //    get() = Retrofit.getClient()
        return mutableLiveData
    }
}