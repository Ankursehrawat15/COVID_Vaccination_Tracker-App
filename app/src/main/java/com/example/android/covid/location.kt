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
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.android.covid.AdapterRV.LocationWise_VcRVAdapter
import com.example.android.covid.AdapterRV.VaccinationCenterRV
import com.example.android.covid.Model.CenterRVModel
import com.example.android.covid.Model.LocationWiseCenterModel
import com.google.android.gms.location.*
import org.json.JSONException
import kotlin.properties.Delegates

class location : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var loadingPB : ProgressBar
   private  var latitude :String = ""
    private var longitude : String = ""
    lateinit var centerList: List<LocationWiseCenterModel>
    lateinit var centerRV : RecyclerView
    lateinit var centerRVAdapter: LocationWise_VcRVAdapter


    private val callback = object: LocationCallback(){
        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }

        override fun onLocationResult(result: LocationResult) {

            val lastLocation = result.lastLocation

             latitude = lastLocation.latitude.toString()
            longitude = lastLocation.longitude.toString()
            super.onLocationResult(result)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        loadingPB = findViewById(R.id.PBLoading_VC)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
         onGPS()
        requestLocation()

        val getData : Button = findViewById(R.id.fetchData)

        centerRV = findViewById(R.id.vaccinationCenterRV)
        centerList = ArrayList<LocationWiseCenterModel>()
        getData.setOnClickListener {
            loadingPB.setVisibility(View.VISIBLE)
            if(!(latitude.isEmpty() && longitude.isEmpty())) {
                getVaccineDetails(latitude, longitude)
            }
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

    private fun getVaccineDetails(lat : String  , lon : String){
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/centers/public/findByLatLong?lat="+lat+"&long="+lon
        val queue = Volley.newRequestQueue(this@location)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            loadingPB.setVisibility(View.GONE)
            try {
                val centerArray = response.getJSONArray("centers")
                if(centerArray.length() == 0){
                    Toast.makeText(this,"No Vaccination Centers Available Near your Location", Toast.LENGTH_LONG).show()

                }
                for(i in 0 until centerArray.length()){
                    val centerObj = centerArray.getJSONObject(i)
                    val center_Name: String = centerObj.getString("name")
                    val center_Address: String = centerObj.getString("location")
                    val center_Id : Int = centerObj.getInt("center_id")
                    val center_state : String = centerObj.getString("state_name")
                    val center_district : String = centerObj.getString("district_name")

                    val center = LocationWiseCenterModel(center_Id , center_Name ,center_district, center_state , center_Address)
                    centerList = centerList+center
                }

                centerRVAdapter = LocationWise_VcRVAdapter(centerList)
                centerRV.layoutManager = LinearLayoutManager(this)
                centerRV.adapter = centerRVAdapter


            }catch (e: JSONException){
                e.printStackTrace()
            }

        },
                {
                    error ->
                    loadingPB.setVisibility(View.GONE)
                    Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
                })
        queue.add(request)
    }



}