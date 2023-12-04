package com.example.weatherreport

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.weatherreport.databinding.ActivityMainBinding
import com.example.weatherreport.viewmodels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currTemp: String = "0.0"
        var currWC: Int = 0

        var mt = 0
        var dt = 0
        var et = 0
        var nt = 0
        var minTomorrowTemp = 50
        var maxTomorrowTemp = -50
        var minAfterTomorrowTemp = 50
        var maxAfterTomorrowTemp = -50
        var afterAfterTommorowString = ""
        var minAfterAfterTomorrowTemp = 50
        var maxAfterAfterTomorrowTemp = -50


        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        weatherViewModel.sosat()
        weatherViewModel.weather.observe(
            this
        ) { weather ->
            weather?.let {
                Log.e("DA", weather.toString())
                currTemp = weather.current.temperature_2m
                    .roundToLong().toString()
                Log.e("Ну и где?", weather.current.temperature_2m.toString())
                currWC = weather.current
                    .weather_code
                for (i in 0..5) mt += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 6..11) dt += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 12..17) et += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 18..23) nt += weather.hourly.temperature_2m.get(i).toInt()

                afterAfterTommorowString = weather.daily.time.get(3).subSequence(9, 10).toString()

                Log.e("Проверка получения даты", weather.daily.time.get(3).substring(6, 8))
                when(weather.daily.time.get(3).substring(5,7)) {
                    "12" -> afterAfterTommorowString += " декабря"
                    "11" -> afterAfterTommorowString += " ноября"
                    "10" -> afterAfterTommorowString += " октября"
                    "9" -> afterAfterTommorowString += "сентября"
                }

                with(binding) {
                    morningTemp.text = (mt / 6).toString() + weather.current_units.temperature_2m
                    with(weather.daily) {
                        minTomorrowTemp = temperature_2m_min.get(1).toInt()
                        minAfterTomorrowTemp = temperature_2m_min.get(2).toInt()

                        maxTomorrowTemp = temperature_2m_max.get(1).toInt()
                        maxAfterTomorrowTemp = temperature_2m_max.get(2).toInt()

                        minAfterAfterTomorrowTemp = temperature_2m_min.get(3).toInt()
                        maxAfterAfterTomorrowTemp = temperature_2m_max.get(3).toInt()
                    }


                    dayTemp.text = (dt / 6).toString() + weather.current_units.temperature_2m
                    eveningTemp.text = (et / 6).toString() + weather.current_units.temperature_2m
                    nightTemp.text = (nt / 6).toString() + weather.current_units.temperature_2m
                    tomorrowTempTV.text = "${minTomorrowTemp}${weather.current_units.temperature_2m}...${maxTomorrowTemp}${weather.current_units.temperature_2m}"
                    afterTomorrowTempTV.text = "${minAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterTomorrowTemp}${weather.current_units.temperature_2m}"

                    afterTomorrowTempStr.text = afterAfterTommorowString
                    afterAfterTomorrowTempTV.text = "${minAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}"

                    currentTemp.text = currTemp + weather.current_units.temperature_2m
                    if (weather.current.weather_code > 50) view.setBackgroundResource(R.drawable.gradient_background_darksky)
                    else view.setBackgroundResource(R.drawable.gradient_background)

                    when (weather.current.weather_code) {
                        0 -> weatherIcon.setImageResource(R.drawable.sunny_svgrepo_com)
                        in 1..3 -> weatherIcon.setImageResource(R.drawable.partly_cloudy_svgrepo_com)
                        in 45..48 -> weatherIcon.setImageResource(R.drawable.fog_svgrepo_com)
                        in 51..67 -> weatherIcon.setImageResource(R.drawable.rain_2_svgrepo_com)
                        in 71..86 -> weatherIcon.setImageResource(R.drawable.snow_alt_1_svgrepo_com)
                    }


                }
            }
        }


    }
}