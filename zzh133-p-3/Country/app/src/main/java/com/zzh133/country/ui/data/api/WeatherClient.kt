package com.zzh133.country.ui.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {
    private const val BASE_URL = "https://api.open-meteo.com/"
//    https://api.open-meteo.com/v1/forecast?latitude=43.1566&longitude=-77.6088&current=temperature_2m,wind_speed_10m
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}