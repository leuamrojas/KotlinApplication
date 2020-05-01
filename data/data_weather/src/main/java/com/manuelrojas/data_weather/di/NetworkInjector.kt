package com.manuelrojas.data_weather.di

object NetworkInjector {

    lateinit var networkComponent: NetworkComponent

    fun initialize() {
        networkComponent = DaggerNetworkComponent.builder().build()
    }
}