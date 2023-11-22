package com.example.weatherreport.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherreport.WeatherService
import com.example.weatherreport.models.Response

class WeatherRepository {
    val weatherService = WeatherService()
    suspend fun getData(lon: Double, lat: Double): MutableLiveData<Response> {
        var mutableLiveData = MutableLiveData<Response>()
        mutableLiveData.value = weatherService.fetchWeatherByCityName(lon, lat)
        Log.e("СУКА БЛЯТЬ1", mutableLiveData.value.toString())
        return mutableLiveData
    }
}