package com.manuelrojas.feature_weather.di

import com.manuelrojas.core.di.CoreInjector
import com.manuelrojas.domain_weather.di.WeatherDomainInjector

object WeatherInjector {

    lateinit var component: WeatherComponent

    fun initialize() {
        component = DaggerWeatherComponent.builder()
                .weatherDomainComponent(WeatherDomainInjector.component)
                .coreComponent(CoreInjector.coreComponent)
                .build()
    }
}