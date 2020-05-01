package com.manuelrojas.domain_weather.di

import com.manuelrojas.domain_weather.usecase.GetWeatherUseCase
import dagger.Component

@Component(modules = [WeatherDomainModule::class])
interface WeatherDomainComponent {

    fun getGetWeatherUseCase2(): GetWeatherUseCase
}