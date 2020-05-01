package com.manuelrojas.data_weather.di

import com.manuelrojas.core.di.CoreInjector

object DatabaseInjector {

    lateinit var databaseComponent: DatabaseComponent

    fun initialize() {
        databaseComponent = DaggerDatabaseComponent.builder()
                .coreComponent(CoreInjector.coreComponent)
                .build()
    }
}