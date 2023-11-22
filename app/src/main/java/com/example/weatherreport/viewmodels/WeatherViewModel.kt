package com.example.weatherreport.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherreport.WeatherService
import com.example.weatherreport.repositories.WeatherRepository
import org.json.JSONObject

class WeatherViewModel : ViewModel() {
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

    suspend fun sosat() {

        val dataJSON = weatherRepository.getData(lon, lat)
        Log.e("СУКА БЛЯТЬ", dataJSON.toString())
        val data = dataJSON.value
        val current = data?.current
        currTemp = current?.temperature_2m.toString()
        currWC = current?.weathercode!!

        val hourly = data.hourly
        val timeTemp = hourly.temperature_2m

        for (i in 0..5) {
            nightAvgTemp += timeTemp[i]
        }
        for (i in 6..11) {
            morningAvgTemp += timeTemp[i]
        }
        for (i in 12..17) {
            dayAvgTemp += timeTemp[i]
        }
        for (i in 18..24) {
            eveningAvgTemp += timeTemp[i]
        }
        for (i in 24..47) {
            if (minTomorrowTemp > timeTemp[i])
                minTomorrowTemp = timeTemp[i]
            if (maxTomorrowTemp < timeTemp[i])
                maxTomorrowTemp = timeTemp[i]
        }

        for (i in 48..71) {
            if (minAfterTomorrowTemp > timeTemp[i])
                minAfterTomorrowTemp = timeTemp[i]
            if (maxAfterTomorrowTemp < timeTemp[i])
                maxAfterTomorrowTemp = timeTemp[i]
        }
        morningAvgTemp /= 6
        dayAvgTemp /= 6
        eveningAvgTemp /= 6
        nightAvgTemp /= 6
        Log.e("СЮДА СМОТРИ", currTemp)
    }
}
