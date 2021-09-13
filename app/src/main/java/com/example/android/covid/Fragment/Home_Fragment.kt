package com.example.android.covid.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.android.covid.AdapterRV.StateRecyclerView
import com.example.android.covid.Model.StateModel_India
import com.example.android.covid.R
import org.json.JSONException


class Home_Fragment : Fragment() {

    lateinit var todayCases: TextView
    lateinit var todaydeathsCases: TextView
    lateinit var todayrecoveredCases: TextView
    lateinit var criticalCases: TextView
    lateinit var affectedCountries: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view : View =  inflater.inflate(R.layout.fragment_home_, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todayCases = view.findViewById(R.id.globetodayCases)
        todaydeathsCases = view.findViewById(R.id.globeDeaths)
        todayrecoveredCases = view.findViewById(R.id.globetodayRecovered)
        criticalCases = view.findViewById(R.id.globeCriticalCases)
        affectedCountries = view.findViewById(R.id.countries_Affected)

        getTodayInfo()
    }


    private fun getTodayInfo(){
        val url = "https://corona.lmao.ninja/v2/all"
        val queue = Volley.newRequestQueue(context)
        val request =
                JsonObjectRequest(Request.Method.GET,url,null,{
                    response ->
                    try {
                        val todayCase:Int = response.getInt("todayCases")
                        val todaydeath: Int = response.getInt("todayDeaths")
                        val todayrecover: Int = response.getInt("todayRecovered")
                        val criticalcas: Int = response.getInt("critical")
                        val affected : Int = response.getInt("affectedCountries")
                        todayCases.text = todayCase.toString()
                        todaydeathsCases.text = todaydeath.toString()
                        todayrecoveredCases.text= todayrecover.toString()
                        criticalCases.text = criticalcas.toString()
                        affectedCountries.text = affected.toString()
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }, {
                    error->
                    Toast.makeText(context,"Fail to get the Data", Toast.LENGTH_SHORT).show()

                })
        queue.add(request)
    }





}