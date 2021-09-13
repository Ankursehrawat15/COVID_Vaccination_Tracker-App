package com.example.android.covid.AdapterRV

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid.Model.CenterRVModel
import com.example.android.covid.R

class VaccinationCenterRV(private val centerList: List<CenterRVModel> ) : RecyclerView.Adapter<VaccinationCenterRV.CenterRVViewHolder>() {

    class CenterRVViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val centerNameTV: TextView = itemView.findViewById(R.id.idTVCenterName)
        val centerLocationTV: TextView = itemView.findViewById(R.id.idTVCenterLocation)
        val centerTimingTV: TextView = itemView.findViewById(R.id.TVcenterTimmings)
        val vaccineNameTV: TextView = itemView.findViewById(R.id.TvVaccineName)
        val ageLimitTV: TextView = itemView.findViewById(R.id.TvAgeLimit)
        val vaccineAvailableTV: TextView = itemView.findViewById(R.id.TvVaccineAvailable)
        val vaccineFeeTV: TextView = itemView.findViewById(R.id.TvVaccineFee)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CenterRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.center_rv_item , parent , false)
        return CenterRVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CenterRVViewHolder, position: Int) {
        val center = centerList[position]
        holder.centerNameTV.text = center.centerName
        holder.centerLocationTV.text = center.centerAddress
        holder.centerTimingTV.text = (""+center.centerFromTime+" To: "+center.centerToTime)
        holder.vaccineNameTV.text = center.vaccineName
        holder.vaccineFeeTV.text = center.feeType
        holder.ageLimitTV.text = ("Min Age Limit: "+center.ageLimit.toString())
        holder.vaccineAvailableTV.text = ("Available: "+center.availabilityCapacity.toString())
    }

    override fun getItemCount(): Int {
        return centerList.size
    }
}