package com.example.weatherreport

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherreport.databinding.ActivityMainBinding
import com.example.weatherreport.viewmodels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.roundToLong

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val weatherViewModel: WeatherViewModel = WeatherViewModel()


    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val currentTempTV: TextView = binding.currentTemp
        val morningTempTV: TextView = binding.morningTemp
        val dayTempTV: TextView = binding.dayTemp
        val eveningTempTV: TextView = binding.eveningTemp
        val nightTempTV: TextView = binding.nightTemp
        val tomorrowTempTV = binding.tomorrowTemp
        val afterTomorrowTempTV = binding.afterTomorrowTemp

        Log.e("СЮДА СМОТРИ", "ПЕРЕД СОСАНИЕМ")
        GlobalScope.launch(Dispatchers.Main) {
            weatherViewModel.sosat()
            Log.e("СЮДА СМОТРИ", weatherViewModel.currTemp)




            var currTemp: String = weatherViewModel.currTemp
            var currWC: Int = weatherViewModel.currWC

            if (currWC > 50) {
                view.setBackgroundResource(R.drawable.gradient_background_darksky)
            } else {
                view.setBackgroundResource(R.drawable.gradient_background)
            }
            morningTempTV.setText((weatherViewModel.morningAvgTemp ).roundToLong().toString())
            dayTempTV.setText((weatherViewModel.dayAvgTemp ).roundToLong().toString())
            eveningTempTV.setText((weatherViewModel.eveningAvgTemp ).roundToLong().toString())
            nightTempTV.setText((weatherViewModel.nightAvgTemp ).roundToLong().toString())
            tomorrowTempTV.setText("${weatherViewModel.minTomorrowTemp.roundToLong()}...${weatherViewModel.maxTomorrowTemp.roundToLong()}")
            afterTomorrowTempTV.setText("${weatherViewModel.minAfterTomorrowTemp.roundToLong()}...${weatherViewModel.maxAfterTomorrowTemp.roundToLong()}")

            currentTempTV.setText(currTemp.toDouble().roundToLong().toString())
        }
    }
}

