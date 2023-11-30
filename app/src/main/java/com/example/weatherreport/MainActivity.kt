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

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        weatherViewModel.sosat()
        weatherViewModel.weather.observe(
            this
        ) { weather ->
            weather?.let {
                currTemp = weather.current.temperature_2m
                    .roundToLong().toString()
                Log.e("Ну и где?", weather.current.temperature_2m.toString())
                currWC = weather.current
                    .weathercode
                for (i in 0..5) mt += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 6..11) dt += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 12..17) et += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 18..23) nt += weather.hourly.temperature_2m.get(i).toInt()

                for (i in 24..47) {
                    if (minTomorrowTemp > weather.hourly.temperature_2m.get(i))
                        minTomorrowTemp = weather.hourly.temperature_2m.get(i).toInt()
                    if (maxTomorrowTemp < weather.hourly.temperature_2m.get(i))
                        maxTomorrowTemp = weather.hourly.temperature_2m.get(i).toInt()
                }

                for (i in 48..71) {
                    if (minAfterTomorrowTemp > weather.hourly.temperature_2m.get(i))
                        minAfterTomorrowTemp = weather.hourly.temperature_2m.get(i).toInt()
                    if (maxAfterTomorrowTemp < weather.hourly.temperature_2m.get(i))
                        maxAfterTomorrowTemp = weather.hourly.temperature_2m.get(i).toInt()
                }
                with(binding) {
                    morningTemp.text = (mt/6).toString()+ weather.current_units.temperature_2m
                    dayTemp.text = (dt/6).toString()+ weather.current_units.temperature_2m
                    eveningTemp.text = (et/6).toString()+ weather.current_units.temperature_2m
                    nightTemp.text = (nt/6).toString()+ weather.current_units.temperature_2m
//                    tomorrowTemp.text = "${minTomorrowTemp}...${maxTomorrowTemp}"
//                    afterTomorrowTemp.text = "${minAfterTomorrowTemp}...${maxAfterTomorrowTemp}"
//
                    currentTemp.text = currTemp + weather.current_units.temperature_2m
                    if (weather.current.weathercode > 50) view.setBackgroundResource(R.drawable.gradient_background_darksky)
                    else view.setBackgroundResource(R.drawable.gradient_background)

                    when(weather.current.weathercode) {
                        0 -> weatherIcon.setImageResource(R.drawable.sunny_svgrepo_com)
                        in 1..3 -> weatherIcon.setImageResource(R.drawable.partly_cloudy_svgrepo_com)
                        in 45..48 -> weatherIcon.setImageResource(R.drawable.fog_svgrepo_com)
                        in 51..67 -> weatherIcon.setImageResource(R.drawable.rain_2_svgrepo_com)
                        in 71..86 -> weatherIcon.setImageResource(R.drawable.snow_alt_1_svgrepo_com)
                    }


                }
            }
        }

//        GlobalScope.launch(Dispatchers.Main) {
//            weatherViewModel.sosat()
//
//            var currTemp: String = weatherViewModel.currTemp
//            var currWC: Int = weatherViewModel.currWC
//
//            if (currWC > 50) {
//                view.setBackgroundResource(R.drawable.gradient_background_darksky)
//            } else {
//                view.setBackgroundResource(R.drawable.gradient_background)
//            }
//

//        }

    }
}