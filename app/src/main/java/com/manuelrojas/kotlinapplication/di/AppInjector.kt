package com.manuelrojas.kotlinapplication.di

import android.app.Application
import android.util.Log
import com.manuelrojas.core.di.CoreInjector

object AppInjector {

    fun initialize(application: Application) {
        initializeCore(application)
    }

    private fun initializeCore(application: Application) {
        CoreInjector.initialize(application)
        Log.d("CoreInjector", "initalize")
    }
}