package com.zzh133.country.ui.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryClient {
    private const val BASE_URL = "https://restcountries.com/"

    val api: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }
}