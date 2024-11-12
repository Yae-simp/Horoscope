package com.example.trialhoroscope.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class HoroscopeResponse (
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: HoroscopeData)

data class HoroscopeData (
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val luck: String)

interface HoroscopeService {
    @GET("{period}")
    suspend fun getHoroscopeData(@Path("period") period: String, @Query("sign") id: String) : HoroscopeResponse
}