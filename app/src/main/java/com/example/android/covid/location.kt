package com.example.android.covid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class location : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val callback = object: LocationCallback(){
        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }

        override fun onLocationResult(result: LocationResult) {

            val lastLocation = result.lastLocation

            findViewById<TextView>(R.id.latitude_textView).text = lastLocation.latitude.toString()
            findViewById<TextView>(R.id.longitude_textView).text = lastLocation.longitude.toString()
            super.onLocationResult(result)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
         onGPS()

        val loc = findViewById<Button>(R.id.button)

        loc.setOnClickListener {
            requestLocation()
        }




    }


    private fun onGPS(){
        if(!isLocationEnabled()){

            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))


        }else{
             fetchLocation()
        }
    }

    private fun fetchLocation() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            if(ActivityCompat.checkSelfPermission(this , android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    , 200 )
                return
            }else{
                requestLocation()
            }
       }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val requestLocation = LocationRequest()
        requestLocation.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        requestLocation.interval = 0
        requestLocation.fastestInterval = 0
        requestLocation.numUpdates = 1
        fusedLocationProviderClient.requestLocationUpdates(requestLocation,callback, Looper.myLooper())

    }


    private fun isLocationEnabled() : Boolean {
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}