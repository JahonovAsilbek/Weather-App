package uz.pdp.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.pdp.weatherapp.databinding.ActivityWeatherBinding
import uz.pdp.weatherapp.view_model.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*


class WeatherActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherBinding
    lateinit var weatherViewModel: WeatherViewModel
    private var latitude = 0.0
    private var longitude = 0.0
    private var API_KEY = "fb2fb1d53560c78a716a3f74d82fba42"

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        if (bundle != null) {
            latitude = bundle.getDouble("latitude")
            longitude = bundle.getDouble("longitude")
        } else Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        weatherViewModel.getWeather(latitude, longitude, API_KEY).observe(this, Observer {

            binding.apply {
                progress.visibility= View.INVISIBLE
                cityName.text = it.name

                when (it.weather!![0].main) {
                    "Clouds" -> cloud.setImageResource(R.drawable.ic_cloud)
                    "Clear" -> cloud.setImageResource(R.drawable.ic_sun)
                    "Rain" -> cloud.setImageResource(R.drawable.ic_rainy)
                    "Snow" -> cloud.setImageResource(R.drawable.ic_snow)
                    "Smoke" -> cloud.setImageResource(R.drawable.ic_smoke)
                }

                degreeMain.text = "${(it.main?.temp?.minus(273.15)?.toInt())}\u00B0"
                status.text = it.weather!![0].main

                val currentDate = Date(System.currentTimeMillis())
                val simpleFormat = SimpleDateFormat("EEEE, MMMM d")
                date.text = simpleFormat.format(currentDate).toString()

                windSpeed.text = "${it.wind?.speed} m/s"
                humidityPercent.text = "${it.main?.humidity} %"
                maxTemp.text = "${(it.main?.temp_max?.minus(273.15)?.toInt())}\u00B0"
                minTemp.text = "${(it.main?.temp_min?.minus(273.15)?.toInt())}\u00B0"

                val date = Date((it.sys?.sunrise?.plus(it.timezone!!))!!.toLong())
                val date1 = Date((it.sys?.sunset?.plus(it.timezone!!))!!.toLong())
                val format = SimpleDateFormat("HH:mm").format(date)
                val format1 = SimpleDateFormat("HH:mm").format(date1)

                sunrise.text = format
                sunset.text = format1

                backBtn.setOnClickListener { finish() }
            }

        })
    }
}