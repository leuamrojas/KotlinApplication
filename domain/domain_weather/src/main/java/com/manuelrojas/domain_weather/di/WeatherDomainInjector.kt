package com.manuelrojas.domain_weather.di

import com.manuelrojas.core.rx.SchedulerProvider
import com.manuelrojas.domain_weather.repository.WeatherRepository
import javax.inject.Provider

object WeatherDomainInjector {

    lateinit var component: WeatherDomainComponent

    fun initialize(repository: Provider<WeatherRepository>, schedulerProvider: SchedulerProvider) {
        component = DaggerWeatherDomainComponent.builder()
                .weatherDomainModule(
                        WeatherDomainModule(
                                repository = repository,
                                schedulerProvider = schedulerProvider
                        )
                )
                .build()
    }

}