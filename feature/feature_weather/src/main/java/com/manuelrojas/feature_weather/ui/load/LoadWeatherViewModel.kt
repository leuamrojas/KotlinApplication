package com.manuelrojas.feature_weather.ui.load

import com.manuelrojas.core.*
import com.manuelrojas.core.extensions.setError
import com.manuelrojas.core.extensions.setLoading
import com.manuelrojas.core.extensions.setSuccess
import com.manuelrojas.domain_weather.model.WeatherInfo
import com.manuelrojas.domain_weather.usecase.GetWeatherUseCase
import com.manuelrojas.feature_weather.common.NO_INTERNET_ERROR
import com.manuelrojas.feature_weather.model.WeatherView
import com.manuelrojas.feature_weather.model.mapToView
import com.manuelrojas.feature_weather.ui.base.BaseViewModel
import javax.inject.Inject

class LoadWeatherViewModel @Inject constructor(
        private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel<WeatherView>() {

    fun getWeatherData(location: String) {
        if (!connectivity.hasNetworkAccess())
            onGetWeatherError(NO_INTERNET_ERROR)
        else
            onGetWeatherLoading()

        getWeatherUseCase.run {
            clear()
            buildUseCaseParams( GetWeatherUseCase.Params.buildParams(location) )
            executeUseCase {
                handleResult( it )
            }
        }
    }

    private fun handleResult(result: Result<WeatherInfo>) {
        result.onSuccess { onGetWeatherSuccess(it) }
                .onFailure { onGetWeatherError(it.throwable.message) }
    }

    private fun onGetWeatherSuccess(weatherInfo: WeatherInfo) {
        _getWeatherLiveData.setSuccess(weatherInfo.mapToView())
    }

    private fun onGetWeatherError(message: String? = "") {
        _getWeatherLiveData.setError(message)
    }

    private fun onGetWeatherLoading() {
        _getWeatherLiveData.setLoading()
    }

    override fun onCleared() {
        super.onCleared()
        getWeatherUseCase.clear()
    }
}