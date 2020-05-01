package com.manuelrojas.feature_weather.model

import android.os.Parcelable
import com.manuelrojas.domain_weather.model.WeatherInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherView (
        val city: String,
        val time: String,
        val summary: String,
        val temperature: Double,
        val apparentTemperature: Double,
        val humidity: Double,
        val pressure: Double,
        val visibility: Double,
        val windSpeed: Double,
        val precipProbability: Double,
        val icon: String
) : Parcelable

fun WeatherInfo.mapToView(): WeatherView = WeatherView (
        this.city,
        this.time,
        this.summary,
        this.temperature,
        this.apparentTemperature,
        this.humidity,
        this.pressure,
        this.visibility,
        this.windSpeed,
        this.precipProbability,
        this.icon )