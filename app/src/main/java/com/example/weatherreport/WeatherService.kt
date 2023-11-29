package com.example.weatherreport

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherreport.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

class WeatherService {
//    suspend fun fetchWeatherByCityName(lat: Double, lon: Double): Response? {
//        Log.e("Проверка респонза", "До начала операций")
//        var result = MutableLiveData<Response>()
//
//
//        val retrofitApi = retrofit.create(RetrofitAPI::class.java)
//        val call: Call<Response?>? = retrofitApi.getWeather()
//
//        Log.e("Проверка респонза", call.toString())
//        call!!.enqueue(object : Callback<Response?> {
//            override fun onResponse(
//                call: Call<Response?>?,
//                response: retrofit2.Response<Response?>
//            ) {
//                Log.e("Проверка респонза", response.body().toString())
//                if (response.isSuccessful()) {
//                    Log.e("СУКА БЛЯТЬ2", response.body().toString())
//                    result.value = response.body()
//                }
//            }
//
//
//            override fun onFailure(call: Call<Response?>, t: Throwable) {
//                Log.e("ПИЗДЕЦ НАХУЙ БЛЯТЬ", "Ошибка")
//                TODO("Not yet implemented")
//            }
//        })
////        val url = URL(""https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current=temperature_2m,weathercode&hourly=temperature_2m,weathercode)
////        val dataJSON: String;
////        val result = withContext(Dispatchers.IO) {
////            with(url.openConnection() as HttpURLConnection) {
////                requestMethod = "GET"
////                inputStream.bufferedReader().readText()
////            }
////        }
//        return result.value

//    }
}