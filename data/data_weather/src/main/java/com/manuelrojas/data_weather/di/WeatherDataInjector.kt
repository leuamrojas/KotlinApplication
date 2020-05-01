package com.manuelrojas.data_weather.di

import com.manuelrojas.core.di.CoreInjector
import com.manuelrojas.domain_weather.di.WeatherDomainInjector

object WeatherDataInjector {

    lateinit var component: WeatherDataComponent

    fun initialize() {

        DatabaseInjector.initialize()
        NetworkInjector.initialize()

        component = DaggerWeatherDataComponent.builder()
                .coreComponent(CoreInjector.coreComponent)
                .databaseComponent(DatabaseInjector.databaseComponent)
                .networkComponent(NetworkInjector.networkComponent)
                .build()

        WeatherDomainInjector.initialize(
                component.getWeatherRepository(),
                component.getSchedulerProvider()
        )
    }

}