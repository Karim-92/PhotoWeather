package com.karim.photoweather.network

import com.karim.photoweather.BuildConfig
import com.karim.photoweather.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET
    suspend fun  getWeatherDetails(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("appid") apiKey: String = BuildConfig.API_KEY,
        @Query("units") units: String="metric"
    ) : WeatherModel
}