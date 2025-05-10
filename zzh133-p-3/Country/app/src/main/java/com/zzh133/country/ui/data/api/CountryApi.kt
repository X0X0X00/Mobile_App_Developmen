package com.zzh133.country.ui.data.api

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CountryApi {
    @GET("v3.1/all")
    fun fetchCountries(): Call<JsonArray>

    @GET("v3.1/name/{name}")
    fun fetchCountryByName(
        @Path("name") name: String,
        @Query("fullText") fullText: Boolean = true
    ): Call<JsonArray>
}
