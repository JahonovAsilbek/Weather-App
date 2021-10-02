package uz.pdp.weatherapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
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
            val camera = CameraUpdateFactory.newLatLngZoom(place, 5f)
            mMap.addMarker(MarkerOptions().position(place).title("Choosed place"))
            mMap.animateCamera(camera)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        val tashkent = LatLng(41.2995, 69.2401)
        val camera = CameraUpdateFactory.newLatLngZoom(tashkent, 5f)
        mMap.moveCamera(camera)
        mMap.animateCamera(camera)
        mapClick()
        getWeatherClick()
    }

    private fun getWeatherClick() {
        binding.getWeather.setOnClickListener {
            if (latitude != 0.0 && longitude != 0.0) {
                val bundle = Bundle()
                bundle.putDouble("latitude", latitude)
                bundle.putDouble("longitude", longitude)
                val intent = Intent(this, WeatherActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }
}