package com.manuelrojas.data_weather.di

import com.manuelrojas.core.di.CoreComponent
import com.manuelrojas.data_weather.database.WeatherDatabase
import com.manuelrojas.data_weather.database.dao.WeatherDao
import dagger.Component

@DatabaseScope
@Component(modules = [DatabaseModule::class], dependencies = [CoreComponent::class])
interface DatabaseComponent {

    fun getWeatherDatabase(): WeatherDatabase

    fun getWeatherDao(): WeatherDao
}