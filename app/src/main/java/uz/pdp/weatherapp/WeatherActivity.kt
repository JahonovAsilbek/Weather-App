package uz.pdp.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.pdp.weatherapp.databinding.ActivityWeatherBinding
import uz.pdp.weatherapp.view_model.WeatherViewModel

class WeatherActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherBinding
    lateinit var weatherViewModel: WeatherViewModel
    private var latitude = 0.0
    private var longitude = 0.0
    private var API_KEY = "fb2fb1d53560c78a716a3f74d82fba42"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            latitude = bundle.getDouble("latitude")
            longitude = bundle.getDouble("longitude")
            Toast.makeText(this, "$latitude $longitude", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getWeather(latitude, longitude, API_KEY).observe(this, Observer {
            Log.d("AAAA", "$it")
        })
    }
}