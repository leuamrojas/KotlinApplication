package com.manuelrojas.data_weather.di

import com.manuelrojas.data_weather.network.WeatherApi
import dagger.Component
import javax.inject.Singleton

@NetworkScope
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

    fun getWeatherApi(): WeatherApi
}