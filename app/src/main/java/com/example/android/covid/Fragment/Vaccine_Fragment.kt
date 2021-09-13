package com.example.android.covid.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.android.covid.AdapterRV.VaccinationCenterRV
import com.example.android.covid.Model.CenterRVModel
import com.example.android.covid.R
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList


class Vaccine_Fragment : Fragment() {

    private lateinit var searchButton: Button
    lateinit var pinCodeEdit : EditText
    lateinit var centerRV : RecyclerView
    lateinit var loadingPB : ProgressBar
    lateinit var centerList: List<CenterRVModel>
    lateinit var centerRVAdapter: VaccinationCenterRV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view : View =  inflater.inflate(R.layout.fragment_vaccine_, container, false)
        searchButton = view.findViewById(R.id.searchButton)
        pinCodeEdit = view.findViewById(R.id.idEditPinCode)
        centerRV = view.findViewById(R.id.idRvCenter)
        loadingPB = view.findViewById(R.id.PBLoading)
        centerList = ArrayList<CenterRVModel>()

        searchButton.setOnClickListener {
            val pinCode = pinCodeEdit.text.toString()
            if(pinCode.length != 6){
                Toast.makeText(context , "Please enter a valid pin code", Toast.LENGTH_SHORT).show()
            }else{
                (centerList as ArrayList<CenterRVModel>).clear() // clear before searching for new pinCode

                // calendar object for entering date
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val dpd = context?.let { it1 ->
                    DatePickerDialog(it1,
                            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                                loadingPB.setVisibility(View.VISIBLE)
                                val dateStr: String = """$dayOfMonth-${month+1}-$year"""
                                getVaccineDetails(pinCode , dateStr) // API Function
                            },
                            year,month,day
                    )
                }
                dpd?.show()
            }
        }




        return view
    }

    private fun getVaccineDetails(pincode: String , date: String){
        val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pincode+"&date="+date
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(Request.Method.GET,url,null,{
            response ->
            loadingPB.setVisibility(View.GONE)
            try {

                val centerArray = response.getJSONArray("sessions")
                if(centerArray.length().equals(0)){
                    Toast.makeText(context,"No Vaccination Centers Available",Toast.LENGTH_LONG).show()

                }
                for(i in 0 until centerArray.length()){
                    val centerObj = centerArray.getJSONObject(i)
                    val center_Name: String = centerObj.getString("name")
                    val center_Address: String = centerObj.getString("address")

                    val center_FromTime: String = centerObj.getString("from")
                    val center_ToTime: String = centerObj.getString("to")
                    val feeType : String = centerObj.getString("fee_type")
                    val vaccineName : String = centerObj.getString("vaccine")
                    val availability : Int = centerObj.getInt("available_capacity")
                    val agelimit : Int = centerObj.getInt("min_age_limit")

                    val center = CenterRVModel(center_Name,center_Address, center_FromTime,
                            center_ToTime,feeType,agelimit,vaccineName,availability)

                    centerList = centerList+center
                }

                centerRVAdapter = VaccinationCenterRV(centerList)
                centerRV.layoutManager = LinearLayoutManager(activity)
                centerRV.adapter = centerRVAdapter


            }catch (e: JSONException){
                e.printStackTrace()
            }

        },
                {
                    error ->
                    loadingPB.setVisibility(View.GONE)
                    Toast.makeText(context, "Fail to get data",Toast.LENGTH_SHORT).show()
                })
        queue.add(request)
    }

}