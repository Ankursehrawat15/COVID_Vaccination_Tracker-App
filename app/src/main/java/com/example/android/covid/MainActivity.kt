package com.example.android.covid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.android.covid.Fragment.*
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivity : AppCompatActivity() {

  lateinit var bottomNav : ChipNavigationBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        bottomNav = findViewById(R.id.bottom_menu)

        // Fragment Allocation via chip-bar navigation
        bottomNav.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when(it){
                 // add vaccination fragment and cowin as well here only
                R.id.home_button -> fragment = Home_Fragment()
                R.id.Country_button -> fragment = India_fragment()
                R.id.vaccination_button -> fragment = Vaccine_Fragment()
                R.id.nearByCenters_button -> fragment = NearMe()
                R.id.info_button -> fragment = Info_fragment()
            }
            if (fragment != null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container , fragment)
                    .commit()
            }

        }
    }
}