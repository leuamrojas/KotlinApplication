package com.manuelrojas.core.di

import android.app.Application

object CoreInjector {

    lateinit var coreComponent: CoreComponent

    fun initialize(application: Application) {
        coreComponent = DaggerCoreComponent.builder()
            .coreModule(CoreModule(application))
            .build()
    }
}