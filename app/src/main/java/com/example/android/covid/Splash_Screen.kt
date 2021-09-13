package com.example.android.covid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash_Screen : AppCompatActivity() {

    lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash__screen)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000) // 2 second delay
    }
}