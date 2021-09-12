package com.example.android.covid.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.android.covid.AdapterRV.StateRecyclerView
import com.example.android.covid.Model.StateModel_India
import com.example.android.covid.R
import org.json.JSONException


class India_fragment : Fragment() {

    // all the views
    lateinit var confirmCases: TextView
    lateinit var totalDeaths: TextView
    lateinit var totalRecovered:TextView
    lateinit var newCases:TextView
    lateinit var stateList: List<StateModel_India>

    // recycler view and custom adapter
    lateinit var stateRv : RecyclerView
    lateinit var stateRvAdapter: StateRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_india_fragment, container, false)
        confirmCases = view.findViewById(R.id.confirmed_cases)
        totalDeaths = view.findViewById(R.id.total_Deaths)
        totalRecovered = view.findViewById(R.id.total_recovered)
        newCases = view.findViewById(R.id.newCases)
        stateRv = view.findViewById(R.id.indiaRecyclerView)
        stateList = ArrayList<StateModel_India>()

         getInfo()

        return view
    }


    private fun getInfo(){

        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(context)
        val request =
                JsonObjectRequest(Request.Method.GET,url,null,{
                    response ->
                    try {
                        val dataObj = response.getJSONObject("data")
                        val summaryObj = dataObj.getJSONObject("summary")
                        val totalcases:Int = summaryObj.getInt("total")
                        val totaldeaths:Int = summaryObj.getInt("deaths")
                        val totalrecovered:Int = summaryObj.getInt("discharged")
                        val foriegnCases:Int = summaryObj.getInt("confirmedCasesForeign")

                        confirmCases.text = totalcases.toString()
                        totalDeaths.text = totaldeaths.toString()
                        totalRecovered.text = totalrecovered.toString()
                        newCases.text = foriegnCases.toString()


                        val regionalArray = dataObj.getJSONArray("regional")
                        for(i in 0 until regionalArray.length()){
                            val regionalObj = regionalArray.getJSONObject(i)
                            val stateName:String = regionalObj.getString("loc").toString()
                            val cases:Int = regionalObj.getInt("totalConfirmed")
                            val deaths:Int = regionalObj.getInt("deaths")
                            val recovered:Int = regionalObj.getInt("discharged")

                            val stateModel = StateModel_India(stateName,recovered,deaths,cases)

                            stateList = stateList + stateModel


                        }

                        stateRvAdapter = StateRecyclerView(stateList)
                        stateRv.layoutManager = LinearLayoutManager(activity)
                        stateRv.adapter = stateRvAdapter




                    }catch (e:JSONException){
                        e.printStackTrace()
                    }
                }, {
                    error->
                    Toast.makeText(context,"Fail to get the Data",Toast.LENGTH_SHORT).show()

                })
        queue.add(request)
    }

}