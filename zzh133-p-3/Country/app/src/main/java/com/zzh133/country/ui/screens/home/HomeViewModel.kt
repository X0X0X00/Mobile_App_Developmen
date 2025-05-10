
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.zzh133.country.ui.data.api.WeatherClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<JsonObject?>()
    val weatherData: MutableLiveData<JsonObject?> = _weatherData

    init {
        loadWeather()
    }

    fun loadWeather(latitude: Double = 43.1566, longitude: Double = -77.6088) {
        WeatherClient.api.getCurrentWeather(latitude, longitude).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val weatherJson = response.body()
                    _weatherData.value = weatherJson
                    Log.d("HomeViewModel", "Weather loaded successfully: $weatherJson")
                } else {
                    _weatherData.value = null
                    Log.e("HomeViewModel", "Failed to load weather: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                _weatherData.value = null
                Log.e("HomeViewModel", "Weather request failed: ${t.message}", t)
            }
        })
    }
}