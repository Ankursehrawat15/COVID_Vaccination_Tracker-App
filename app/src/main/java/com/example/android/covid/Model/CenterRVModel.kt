package com.example.android.covid.Model

data class CenterRVModel(
    val centerName:String,
    val centerAddress:String,
    val centerFromTime:String,
    val centerToTime:String,
    val feeType:String,
    val ageLimit: Int,
    var vaccineName: String,
    val availabilityCapacity: Int
)