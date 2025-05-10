package com.zzh133.country.ui.screens.country

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.zzh133.country.ui.data.api.CountryClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryListViewModel : ViewModel() {

    private val _countries = MutableLiveData<List<JsonObject>>()
    val countries: LiveData<List<JsonObject>> = _countries

    fun loadCountriesFromApi() {
        CountryClient.api.fetchCountries().enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                if (response.isSuccessful) {
                    val countryList = response.body() ?: JsonArray()
                    val parsedCountries = (0 until countryList.size()).map { countryList[it].asJsonObject }
                    _countries.value = parsedCountries
                    Log.d("CountryListViewModel", "Countries loaded successfully from API: ${parsedCountries.size} countries")
                } else {
                    _countries.value = emptyList()
                    Log.e("CountryListViewModel", "Failed to load countries: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                _countries.value = emptyList()
                Log.e("CountryListViewModel", "Request failed: ${t.message}", t)
            }
        })
    }

    fun loadCountriesFromUri(context: Context, fileUriString: String) {
        try {
            val uri = Uri.parse(fileUriString)
            val inputStream = context.contentResolver.openInputStream(uri)
            val jsonText = inputStream?.bufferedReader()?.use { it.readText() }
            val jsonArray = com.google.gson.JsonParser.parseString(jsonText).asJsonArray
            val parsedCountries = (0 until jsonArray.size()).map { jsonArray[it].asJsonObject }
            _countries.value = parsedCountries
            Log.d("CountryListViewModel", "Countries loaded from selected file: ${parsedCountries.size} countries")
        } catch (e: Exception) {
            _countries.value = emptyList()
            Log.e("CountryListViewModel", "Failed to load countries from selected file", e)
        }
    }
}