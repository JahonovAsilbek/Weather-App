package uz.pdp.weatherapp.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.pdp.weatherapp.models.Weather
import uz.pdp.weatherapp.retrofit.ApiClient

class WeatherViewModel : ViewModel() {

    private var liveData = MutableLiveData<Weather>()

    fun getWeather(latitude: Double, longitude: Double, API_KEY: String): LiveData<Weather> {
        ApiClient.apiService.getWeather(latitude, longitude, API_KEY)
            .enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if (response.isSuccessful) {
                        liveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.d("AAAA", "onFailure: ${t.message}")
                }
            })
        return liveData
    }
}