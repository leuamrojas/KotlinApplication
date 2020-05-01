package com.manuelrojas.domain_weather.repository

import com.manuelrojas.core.Result
import com.manuelrojas.domain_weather.model.WeatherInfo
import io.reactivex.Observable

interface WeatherRepository {

    fun getWeatherForLocation(location: String): Observable<Result<WeatherInfo>>
}