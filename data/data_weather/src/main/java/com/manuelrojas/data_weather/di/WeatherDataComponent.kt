package com.manuelrojas.data_weather.di

import com.manuelrojas.core.di.CoreComponent
import com.manuelrojas.domain_weather.repository.WeatherRepository
import dagger.Component
import javax.inject.Provider

@WeatherDataScope
@Component(modules = [WeatherDataModule::class],
        dependencies = [CoreComponent::class, NetworkComponent::class, DatabaseComponent::class])
interface WeatherDataComponent: CoreComponent {

    fun getWeatherRepository(): Provider<WeatherRepository>
}