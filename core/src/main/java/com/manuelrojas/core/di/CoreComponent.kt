package com.manuelrojas.core.di

import android.app.Application
import android.content.Context
import com.manuelrojas.core.common.Connectivity
import com.manuelrojas.core.rx.SchedulerProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    fun getContext(): Context

    fun getApplication(): Application

    fun getSchedulerProvider(): SchedulerProvider

    fun getConnectivity(): Connectivity

//    fun getMoshi(): Moshi

}