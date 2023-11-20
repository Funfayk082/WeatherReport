package com.example.weatherreport.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherreport.WeatherService
import org.json.JSONObject

class WeatherViewModel : ViewModel() {
    val weatherService: WeatherService = WeatherService()
    var lat: Double = 57.752773675045106
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

    suspend fun sosat() {

        val dataJSON = weatherService.fetchWeatherByCityName(lat, lon)
        val data = JSONObject(dataJSON)
        val current = data.getJSONObject("current")
        currTemp = current.get("temperature_2m").toString()
        currWC = current.get("weathercode").toString().toInt()

        val hourly = data.getJSONObject("hourly")
        val timeTemp = hourly.getJSONArray("temperature_2m")

        for (i in 0..5) {
            nightAvgTemp += timeTemp.get(i).toString().toDouble()
        }
        for (i in 6..11) {
            morningAvgTemp += timeTemp.get(i).toString().toDouble()
        }
        for (i in 12..17) {
            dayAvgTemp += timeTemp.get(i).toString().toDouble()
        }
        for (i in 18..24) {
            eveningAvgTemp += timeTemp.get(i).toString().toDouble()
        }
        for (i in 24..47) {
            if (minTomorrowTemp > timeTemp.get(i).toString().toDouble())
                minTomorrowTemp = timeTemp.get(i).toString().toDouble()
            if (maxTomorrowTemp < timeTemp.get(i).toString().toDouble())
                maxTomorrowTemp = timeTemp.get(i).toString().toDouble()
        }

        for (i in 48..71) {
            if (minAfterTomorrowTemp > timeTemp.get(i).toString().toDouble())
                minAfterTomorrowTemp = timeTemp.get(i).toString().toDouble()
            if (maxAfterTomorrowTemp < timeTemp.get(i).toString().toDouble())
                maxAfterTomorrowTemp = timeTemp.get(i).toString().toDouble()
        }
        morningAvgTemp /= 6
        dayAvgTemp /= 6
        eveningAvgTemp /= 6
        nightAvgTemp /= 6
        Log.e("СЮДА СМОТРИ", currTemp)
    }
}
