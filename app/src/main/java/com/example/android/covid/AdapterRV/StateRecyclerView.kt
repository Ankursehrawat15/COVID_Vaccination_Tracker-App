package com.example.android.covid.AdapterRV

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid.Model.StateModel_India
import com.example.android.covid.R


class StateRecyclerView(private val stateList:List<StateModel_India> ) : RecyclerView.Adapter<StateRecyclerView.StateViewHolder>() {

    // ViewHolder
    class StateViewHolder(item : View) : RecyclerView.ViewHolder(item){
          val stateNameTV:TextView = item.findViewById(R.id.rv_indiaState)
          val totalCasesTV:TextView = item.findViewById(R.id.rv_india_totalCases)
          val recoveredTV:TextView = item.findViewById(R.id.rv_india_recoveredCases)
          val deathTV:TextView = item.findViewById(R.id.rv_india_deathCases)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_inside_rv , parent,false)
        return StateViewHolder(itemView)
    }
     // set data to each of the views
    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
       val stateData = stateList[position]
        holder.totalCasesTV.text = stateData.totalCases.toString()
         holder.recoveredTV.text = stateData.recovered.toString()
         holder.deathTV.text = stateData.deaths.toString()
         holder.stateNameTV.text = stateData.state

    }

    override fun getItemCount(): Int {
       return stateList.size
    }


}