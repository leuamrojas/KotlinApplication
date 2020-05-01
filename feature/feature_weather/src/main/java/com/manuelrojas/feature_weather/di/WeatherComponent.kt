package com.manuelrojas.feature_weather.di

import com.manuelrojas.core.di.CoreComponent
import com.manuelrojas.domain_weather.di.WeatherDomainComponent
import com.manuelrojas.feature_weather.ui.base.BaseFragment
import com.manuelrojas.feature_weather.ui.load.ViewModelFactory
import com.manuelrojas.feature_weather.ui.load.ViewModelModule
import dagger.Component

@WeatherScope
@Component(modules = [ViewModelModule::class], dependencies = [WeatherDomainComponent::class, CoreComponent::class])
interface WeatherComponent {

    fun getWeatherViewModelFactory() : ViewModelFactory

    fun inject(baseFragment: BaseFragment)
}