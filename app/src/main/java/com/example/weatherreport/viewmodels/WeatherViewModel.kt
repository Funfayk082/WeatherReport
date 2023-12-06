package com.example.weatherreport.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.weatherreport.ApiConfig
import com.example.weatherreport.models.Response
import com.example.weatherreport.repositories.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import kotlin.math.log

class WeatherViewModel : ViewModel() {
    private val _weather = MutableLiveData<Response>()
    val weather: LiveData<Response> = _weather

    private val _dt = MutableLiveData<Int>()
    var dt: LiveData<Int> = _dt
    private val _mt = MutableLiveData<Int>()
    var mt: LiveData<Int> = _mt
    private val _et = MutableLiveData<Int>()
    var et: LiveData<Int> = _et
    private val _nt = MutableLiveData<Int>()
    var nt: LiveData<Int> = _nt

    val weatherRepository: WeatherRepository = WeatherRepository()
    var lat: Double = 57.7665
    var lon: Double = 40.9269

    fun sosat() {
        val client = ApiConfig.getApiService().getWeather(lat, lon, "temperature_2m,apparent_temperature,weather_code", "temperature_2m","weather_code,temperature_2m_max,temperature_2m_min")
        client?.enqueue(object : Callback<Response?> {
            @SuppressLint("CheckResult")
            override fun onResponse(
                call: Call<Response?>,
                response: retrofit2.Response<Response?>
            ) {
                if (response.isSuccessful) {
                    _weather.value = response.body()
                    _nt.value = _weather.value?.hourly?.temperature_2m?.slice(24..29)?.average()?.toInt()
                    _mt.value = _weather.value?.hourly?.temperature_2m?.slice(6..11)?.average()?.toInt()
                    _et.value = _weather.value?.hourly?.temperature_2m?.slice(18..23)?.average()?.toInt()
                    _dt.value = _weather.value?.hourly?.temperature_2m?.slice(12..17)?.average()?.toInt()
                }
            }

            override fun onFailure(call: Call<Response?>, t: Throwable) {
            }
        })
    }
}