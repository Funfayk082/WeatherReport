package com.example.weatherreport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherreport.ApiConfig
import com.example.weatherreport.models.Response
import com.example.weatherreport.repositories.WeatherRepository
import retrofit2.Call
import retrofit2.Callback

class WeatherViewModel : ViewModel() {
    private val _weather = MutableLiveData<Response>()
    val weather: LiveData<Response> = _weather

    val weatherRepository: WeatherRepository = WeatherRepository()
    var lat: Double = 57.7665
    var lon: Double = 40.9269

    fun sosat() {
        val client = ApiConfig.getApiService().getWeather(lat, lon, "temperature_2m,apparent_temperature,weather_code", "temperature_2m","weather_code,temperature_2m_max,temperature_2m_min")
        client?.enqueue(object : Callback<Response?> {
            override fun onResponse(
                call: Call<Response?>,
                response: retrofit2.Response<Response?>
            ) {
                if (response.isSuccessful) {
                    _weather.value = response.body()
                }
            }

            override fun onFailure(call: Call<Response?>, t: Throwable) {
            }
        })
    }
}