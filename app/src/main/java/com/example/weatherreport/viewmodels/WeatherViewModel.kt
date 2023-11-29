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
    var lat: Double = 0.0
    var lon: Double = 40.98726217391432

    var currTemp: String = ""
    var currWC: Int = 0

    var dayAvgTemp = 0.0
    var eveningAvgTemp = 0.0
    var nightAvgTemp = 0.0
    var morningAvgTemp = 0.0

    var minTomorrowTemp = 50.0
    var maxTomorrowTemp = -50.0
    var minAfterTomorrowTemp = 50.0
    var maxAfterTomorrowTemp = -50.0

    fun sosat() {
        val client = ApiConfig.getApiService().getWeather(lat, lon, "temperature_2m,weathercode", "temperature_2m,weathercode")
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
//
//        val dataJSON = weatherRepository.getData(lon, lat)
//        Log.e("СУКА БЛЯТЬ", dataJSON.toString())
//        val data = dataJSON.value
//        val current = data?.current
//        currTemp = current?.temperature_2m.toString()
//        currWC = current?.weathercode!!
//
//        val hourly = data.hourly
//        val timeTemp = hourly.temperature_2m
//
//        for (i in 0..5) {
//            nightAvgTemp += timeTemp[i]
//        }
//        for (i in 6..11) {
//            morningAvgTemp += timeTemp[i]
//        }
//        for (i in 12..17) {
//            dayAvgTemp += timeTemp[i]
//        }
//        for (i in 18..24) {
//            eveningAvgTemp += timeTemp[i]
//        }
//        for (i in 24..47) {
//            if (minTomorrowTemp > timeTemp[i])
//                minTomorrowTemp = timeTemp[i]
//            if (maxTomorrowTemp < timeTemp[i])
//                maxTomorrowTemp = timeTemp[i]
//        }
//
//        for (i in 48..71) {
//            if (minAfterTomorrowTemp > timeTemp[i])
//                minAfterTomorrowTemp = timeTemp[i]
//            if (maxAfterTomorrowTemp < timeTemp[i])
//                maxAfterTomorrowTemp = timeTemp[i]
//        }
//        morningAvgTemp /= 6
//        dayAvgTemp /= 6
//        eveningAvgTemp /= 6
//        nightAvgTemp /= 6
//        Log.e("СЮДА СМОТРИ", currTemp)
    }
}