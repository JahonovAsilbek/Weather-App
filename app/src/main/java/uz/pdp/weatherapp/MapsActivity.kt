package uz.pdp.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uz.pdp.weatherapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    private fun mapClick() {
        mMap.setOnMapClickListener {
            latitude = it.latitude
            longitude = it.longitude
            mMap.clear()
            val place = LatLng(it.latitude, it.longitude)
            mMap.addMarker(MarkerOptions().position(place).title("Choosed place"))
            Toast.makeText(this, "${it.latitude} ${it.longitude} ", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        mapClick()
        getWeatherClick()
    }

    private fun getWeatherClick() {
        binding.getWeather.setOnClickListener {
            val bundle = Bundle()
            bundle.putDouble("latitude", latitude)
            bundle.putDouble("longitude", longitude)
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}