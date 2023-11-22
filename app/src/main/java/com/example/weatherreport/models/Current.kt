package com.example.weatherreport.models

data class Current(
    val interval: Int,
    val temperature_2m: Double,
    val time: String,
    val weathercode: Int
)