package com.example.weatherreport

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.weatherreport.databinding.ActivityMainBinding
import com.example.weatherreport.viewmodels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        GlobalScope.launch(Dispatchers.Main) {
            weatherViewModel.sosat()

            var currTemp: String = weatherViewModel.currTemp
            var currWC: Int = weatherViewModel.currWC

            if (currWC > 50) {
                view.setBackgroundResource(R.drawable.gradient_background_darksky)
            } else {
                view.setBackgroundResource(R.drawable.gradient_background)
            }

            with(binding) {
                morningTemp.text = (weatherViewModel.morningAvgTemp ).roundToLong().toString()
                dayTemp.text = (weatherViewModel.dayAvgTemp ).roundToLong().toString()
                eveningTemp.text = (weatherViewModel.eveningAvgTemp ).roundToLong().toString()
                nightTemp.text = (weatherViewModel.nightAvgTemp ).roundToLong().toString()
                tomorrowTemp.text = "${weatherViewModel.minTomorrowTemp.roundToLong()}...${weatherViewModel.maxTomorrowTemp.roundToLong()}"
                afterTomorrowTemp.text = "${weatherViewModel.minAfterTomorrowTemp.roundToLong()}...${weatherViewModel.maxAfterTomorrowTemp.roundToLong()}"

                currentTemp.text = currTemp.toDouble().roundToLong().toString()
            }
        }
    }
}