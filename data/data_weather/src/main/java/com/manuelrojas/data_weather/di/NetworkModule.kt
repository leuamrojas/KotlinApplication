package com.manuelrojas.data_weather.di

import com.manuelrojas.data_weather.network.WeatherApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @NetworkScope
    fun provideWeatherApi(): WeatherApi {
        return WeatherApi()
    }

}