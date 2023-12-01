package com.example.weatherreport.models

data class Current(
    val apparent_temperature: Double,
    val interval: Int,
    val temperature_2m: Double,
    val time: String,
    val weather_code: Int
)