package com.karim.photoweather.network

import com.karim.photoweather.model.WeatherModel
import javax.inject.Inject

class WeatherClient @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getWeatherDetails(
        lat: Double,
        lon: Double
    ) : WeatherModel = weatherService.getWeatherDetails(lat, lon)
}