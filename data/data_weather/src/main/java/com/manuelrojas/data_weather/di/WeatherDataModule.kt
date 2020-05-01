package com.manuelrojas.data_weather.di

import com.manuelrojas.data_weather.repository.WeatherRepositoryImpl
import com.manuelrojas.domain_weather.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module(includes = [WeatherDataModule.BindModule::class])
class WeatherDataModule {

    @Module
    interface BindModule{

        @Binds
        fun bindRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
    }

}