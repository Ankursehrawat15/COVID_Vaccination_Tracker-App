package com.example.android.covid.AdapterRV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid.Model.LocationWiseCenterModel
import com.example.android.covid.R

class LocationWise_VcRVAdapter(private val vcList : List<LocationWiseCenterModel>) : RecyclerView.Adapter<LocationWise_VcRVAdapter.VaccinationCenterRVViewHolder>() {


    class VaccinationCenterRVViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.TVVC_CenterName)
        val location : TextView = itemView.findViewById(R.id.idTVVC_CenterLocation)
        val centerId : TextView = itemView.findViewById(R.id.TvVC_Centerid)
        val state : TextView = itemView.findViewById(R.id.TVVC_centerstate)
        val district : TextView = itemView.findViewById(R.id.TvVC_centerdistrict)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationCenterRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.location_vaccinationrv, parent , false)
        return VaccinationCenterRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VaccinationCenterRVViewHolder, position: Int) {
        val list = vcList[position]
        holder.name.text = list.nameCenter
        holder.location.text = list.location_address
        holder.centerId.text = ("CenterID: " + list.centerId.toString())
        holder.state.text = ("State: "+ list.stateName)
        holder.district.text = ("District: "+list.districtName)
    }

    override fun getItemCount(): Int {
       return vcList.size
    }


}