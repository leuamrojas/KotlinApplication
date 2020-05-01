package com.manuelrojas.core.di

import android.app.Application
import android.content.Context
import com.manuelrojas.core.common.Connectivity
import com.manuelrojas.core.common.ConnectivityImpl
import com.manuelrojas.core.rx.DefaultSchedulerProvider
import com.manuelrojas.core.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = DefaultSchedulerProvider()

    @Provides
    @Singleton
    fun provideConnectivity(context: Context): Connectivity = ConnectivityImpl(context)

//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder()
//                .add(KotlinJsonAdapterFactory())
//                .build()
//    }

}