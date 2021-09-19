package com.example.android.covid.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.android.covid.R
import com.example.android.covid.location

class NearMe : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view : View = inflater.inflate(R.layout.fragment_near_me, container, false)

        val locationActivity : Button = view.findViewById(R.id.locationaccess)

        locationActivity.setOnClickListener {
            val intent : Intent = Intent(context,location::class.java)
            startActivity(intent)

        }


        return view
    }



}