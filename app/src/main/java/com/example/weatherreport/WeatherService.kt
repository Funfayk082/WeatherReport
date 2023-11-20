package com.example.weatherreport

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class WeatherService {
    suspend fun fetchWeatherByCityName(lat: Double, lon: Double): String {
        val url = URL("https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current=temperature_2m,weathercode&hourly=temperature_2m,weathercode")
        val dataJSON: String;
        val result = withContext(Dispatchers.IO) {
            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                inputStream.bufferedReader().readText()
            }
        }
        return result

    }
}