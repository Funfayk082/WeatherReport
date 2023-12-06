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
        var unit = ""
        var currWC: Int = 0

        var minTomorrowTemp = 50
        var maxTomorrowTemp = -50
        var minAfterTomorrowTemp = 50
        var maxAfterTomorrowTemp = -50
        var afterAfterTommorowString = ""
        var minAfterAfterTomorrowTemp = 50
        var maxAfterAfterTomorrowTemp = -50
        var minAfterAfterAfterTomorrowTemp = 50
        var maxAfterAfterAfterTomorrowTemp = -50
        var afterAfterAfterTomorrowStr = ""

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        weatherViewModel.sosat()
        weatherViewModel.weather.observe(
            this
        ) { weather ->
            weather?.let {
                unit = weather.current_units.temperature_2m
                currTemp = weather.current.temperature_2m
                    .roundToLong().toString()

                currWC = weather.current
                    .weather_code

                weatherViewModel.dt.observe(this) {
                    binding.dayTemp.text = (weatherViewModel.dt.value).toString() + unit
                    binding.nightTemp.text = (weatherViewModel.nt.value).toString() + unit
                    binding.morningTemp.text = (weatherViewModel.mt.value).toString() + unit
                    binding.eveningTemp.text = (weatherViewModel.et.value).toString() + unit
                }

                afterAfterTommorowString = weather.daily.time.get(3).subSequence(8, 10).toString().toInt().toString()
                afterAfterAfterTomorrowStr = weather.daily.time.get(4).subSequence(8, 10).toString().toInt().toString()

                when(weather.daily.time.get(3).substring(5,7)) {
                    "12" -> afterAfterTommorowString += " декабря"
                    "11" -> afterAfterTommorowString += " ноября"
                    "10" -> afterAfterTommorowString += " октября"
                    "9" -> afterAfterTommorowString += " сентября"
                    "8" -> afterAfterTommorowString += " августа"
                    "7" -> afterAfterTommorowString += " июля"
                    "6" -> afterAfterTommorowString += " июня"
                    "5" -> afterAfterTommorowString += " мая"
                    "4" -> afterAfterTommorowString += " апреля"
                    "3" -> afterAfterTommorowString += " марта"
                    "2" -> afterAfterTommorowString += " февраля"
                    "1" -> afterAfterTommorowString += " января"
                }

                when(weather.daily.time.get(4).substring(5,7)) {
                    "12" -> afterAfterAfterTomorrowStr += " декабря"
                    "11" -> afterAfterAfterTomorrowStr += " ноября"
                    "10" -> afterAfterAfterTomorrowStr  += " октября"
                    "9" -> afterAfterAfterTomorrowStr += " сентября"
                    "8" -> afterAfterAfterTomorrowStr += " августа"
                    "7" -> afterAfterAfterTomorrowStr += " июля"
                    "6" -> afterAfterAfterTomorrowStr += " июня"
                    "5" -> afterAfterAfterTomorrowStr += " мая"
                    "4" -> afterAfterAfterTomorrowStr += " апреля"
                    "3" -> afterAfterAfterTomorrowStr += " марта"
                    "2" -> afterAfterAfterTomorrowStr += " февраля"
                    "1" -> afterAfterAfterTomorrowStr += " января"
                }

                with(binding) {

                    with(weather.daily) {
                        minTomorrowTemp = temperature_2m_min.get(1).toInt()
                        minAfterTomorrowTemp = temperature_2m_min[2].toInt()

                        maxTomorrowTemp = temperature_2m_max.get(1).toInt()
                        maxAfterTomorrowTemp = temperature_2m_max.get(2).toInt()

                        minAfterAfterTomorrowTemp = temperature_2m_min.get(3).toInt()
                        maxAfterAfterTomorrowTemp = temperature_2m_max.get(3).toInt()

                        minAfterAfterAfterTomorrowTemp = temperature_2m_min.get(4).toInt()
                        maxAfterAfterAfterTomorrowTemp= temperature_2m_max.get(4).toInt()
                    }

                    tomorrowTempTV.text = "${minTomorrowTemp}${weather.current_units.temperature_2m}...${maxTomorrowTemp}${weather.current_units.temperature_2m}"
                    afterTomorrowTempTV.text = "${minAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterTomorrowTemp}${weather.current_units.temperature_2m}"

                    afterAfterTomorrowTempStr.text = afterAfterTommorowString
                    afterAfterTomorrowTempTV.text = "${minAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}"
                    afterAfterAfterTomorrowTempStr.text = afterAfterAfterTomorrowStr
                    afterAfterAfterTomorrowTempTV.text = "${minAfterAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}"


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