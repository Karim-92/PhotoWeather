package com.karim.photoweather.model
import android.os.Parcelable
import com.karim.photoweather.utils.ICON_URL

import kotlinx.parcelize.Parcelize

import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherModel(
    @Json(name = "coord") val coord: Coord,
    @Json(name = "weather") val weather: List<Weather>,
    @Json(name = "base") val base: String,
    @Json(name = "main") val main: Main,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "clouds") val clouds: Clouds,
    @Json(name = "dt") val dt: Int,
    @Json(name = "sys") val sys: Sys,
    @Json(name = "timezone") val timezone: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "cod") val cod: Int
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Coord(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Weather(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
) : Parcelable{
    val iconPath
        get() = "${ICON_URL}.${icon}@2x.png"
}

@JsonClass(generateAdapter = true)
@Parcelize
data class Main(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Wind(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val deg: Int
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Clouds(
    @Json(name = "all") val all: Int
) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Sys(
    @Json(name = "type") val type: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: Int,
    @Json(name = "sunset") val sunset: Int
) : Parcelable