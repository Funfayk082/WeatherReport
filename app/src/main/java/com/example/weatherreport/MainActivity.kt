package com.example.weatherreport

import android.annotation.SuppressLint
import android.health.connect.datatypes.units.Length
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherreport.databinding.ActivityMainBinding
import com.example.weatherreport.viewmodels.WeatherViewModel
import kotlin.math.roundToLong


class MainActivity : AppCompatActivity() {
    lateinit var chooseCity: TextView

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currTemp: String
        var currWC = 0
        var currCity = "Кострома"

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
        var minAfterAfterAfterTomorrowTemp = 50
        var maxAfterAfterAfterTomorrowTemp = -50
        var afterAfterAfterTomorrowStr = ""

        var binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        chooseCity = binding.YourCityTV

        weatherViewModel.getDate(57.7665, 40.9269)
        weatherViewModel.weather.observe(
            this
        ) { weather ->
            weather?.let {
                currTemp = weather.current.temperature_2m
                    .roundToLong().toString()
                currWC = weather.current
                    .weather_code
                mt = 0
                dt = 0
                et = 0
                nt = 0
                for (i in 0..5) mt += weather.hourly.temperature_2m.get(i).toInt()
                for (i in 6..11) dt += weather.hourly.temperature_2m[i].toInt()
                for (i in 12..17) et += weather.hourly.temperature_2m[i].toInt()
                for (i in 18..23) nt += weather.hourly.temperature_2m[i].toInt()

                afterAfterTommorowString = weather.daily.time[3].subSequence(8, 10).toString()
                afterAfterAfterTomorrowStr = weather.daily.time[4].subSequence(8, 10).toString()

                when (weather.daily.time[3].substring(5, 7)) {
                    "12" -> afterAfterTommorowString += " декабря"
                    "11" -> afterAfterTommorowString += " ноября"
                    "10" -> afterAfterTommorowString += " октября"
                    "09" -> afterAfterTommorowString += " сентября"
                    "08" -> afterAfterTommorowString += " августа"
                    "07" -> afterAfterTommorowString += " июля"
                    "06" -> afterAfterTommorowString += " июня"
                    "05" -> afterAfterTommorowString += " мая"
                    "04" -> afterAfterTommorowString += " апреля"
                    "03" -> afterAfterTommorowString += " марта"
                    "02" -> afterAfterTommorowString += " февраля"
                    "01" -> afterAfterTommorowString += " января"
                }
                when (weather.daily.time[4].substring(5, 7)) {
                    "12" -> afterAfterAfterTomorrowStr += " декабря"
                    "11" -> afterAfterAfterTomorrowStr += " ноября"
                    "10" -> afterAfterAfterTomorrowStr += " октября"
                    "09" -> afterAfterAfterTomorrowStr += " сентября"
                    "08" -> afterAfterAfterTomorrowStr += " августа"
                    "07" -> afterAfterAfterTomorrowStr += " июля"
                    "06" -> afterAfterAfterTomorrowStr += " июня"
                    "05" -> afterAfterAfterTomorrowStr += " мая"
                    "04" -> afterAfterAfterTomorrowStr += " апреля"
                    "03" -> afterAfterAfterTomorrowStr += " марта"
                    "02" -> afterAfterAfterTomorrowStr += " февраля"
                    "01" -> afterAfterAfterTomorrowStr += " января"
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

                        minAfterAfterAfterTomorrowTemp = temperature_2m_min.get(4).toInt()
                        maxAfterAfterAfterTomorrowTemp = temperature_2m_max.get(4).toInt()
                    }


                    dayTemp.text = (dt / 6).toString() + weather.current_units.temperature_2m
                    eveningTemp.text = (et / 6).toString() + weather.current_units.temperature_2m
                    nightTemp.text = (nt / 6).toString() + weather.current_units.temperature_2m
                    tomorrowTempTV.text =
                        "${minTomorrowTemp}${weather.current_units.temperature_2m}...${maxTomorrowTemp}${weather.current_units.temperature_2m}"
                    afterTomorrowTempTV.text =
                        "${minAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterTomorrowTemp}${weather.current_units.temperature_2m}"

                    afterAfterTomorrowTempStr.text = afterAfterTommorowString
                    afterAfterTomorrowTempTV.text =
                        "${minAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}"
                    afterAfterAfterTomorrowTempStr.text = afterAfterAfterTomorrowStr
                    afterAfterAfterTomorrowTempTV.text =
                        "${minAfterAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}...${maxAfterAfterAfterTomorrowTemp}${weather.current_units.temperature_2m}"


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

        chooseCity.setOnClickListener {
            getLocation()
        }


    }

    fun getLocation() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.choose_city_edit_text, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.choose_city)

        with(builder) {
            setTitle("Выберите город")
            setPositiveButton("Ок") { _, _ ->
                Log.e("edittext", editText.text.toString())
                val geocoder = Geocoder(this@MainActivity)
                var addresses = geocoder.getFromLocationName(editText.text.toString(), 1)!!
                Log.e("geocoder", geocoder.toString())
                if (addresses.size > 0) {
                    chooseCity.text = editText.text.toString()
                    Log.e("Верно!", "Ищу ответ!")
                    weatherViewModel.getDate(
                        addresses[0].latitude,
                        addresses[0].longitude
                    )
                } else {
                    Log.e("Неверно!", "Города нет!!!")
                    Toast.makeText(this@MainActivity, "Город не найден!", Toast.LENGTH_LONG)
                    getLocation()
                }
            }
            setView(dialogLayout)
            show()
        }
    }
}