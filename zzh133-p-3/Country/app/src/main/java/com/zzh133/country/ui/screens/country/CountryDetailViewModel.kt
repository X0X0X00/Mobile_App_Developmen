package com.zzh133.country.ui.screens.country

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

class CountryDetailViewModel : ViewModel() {

    private val _country = MutableLiveData<JsonObject?>()
    val country: LiveData<JsonObject?> = _country

    fun fetchCountry(name: String) {
        CountryClient.api.fetchCountryByName(name).enqueue(object : Callback<JsonArray> {
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>
            ) {
                if (response.isSuccessful) {
                    val countryArray = response.body()
                    val countryObject = countryArray?.firstOrNull()?.asJsonObject

                    if (countryObject != null) {
                        _country.value = countryObject
                        Log.d("CountryDetailViewModel", "Country loaded: $name")
                    } else {
                        _country.value = null
                        Log.e("CountryDetailViewModel", "Country not found in response")
                    }
                } else {
                    _country.value = null
                    Log.e("CountryDetailViewModel", "Failed to load country: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                _country.value = null
                Log.e("CountryDetailViewModel", "Request failed: ${t.message}", t)
            }
        })
    }
}
