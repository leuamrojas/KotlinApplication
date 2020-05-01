package com.manuelrojas.domain_weather.model

data class WeatherInfo(val city: String,
                       val time: String,
                       val summary: String,
                       val temperature: Double,
                       val apparentTemperature: Double,
                       val humidity: Double,
                       val pressure: Double,
                       val visibility: Double,
                       val windSpeed: Double,
                       val precipProbability: Double,
                       val icon: String)