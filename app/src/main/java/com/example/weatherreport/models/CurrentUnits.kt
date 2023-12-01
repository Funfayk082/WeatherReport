package com.example.weatherreport.models

data class CurrentUnits(
    val apparent_temperature: String,
    val interval: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String
)