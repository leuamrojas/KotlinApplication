package com.manuelrojas.domain_weather.usecase

import com.manuelrojas.core.Failure
import com.manuelrojas.core.HttpError
import com.manuelrojas.core.Result
import com.manuelrojas.core.domain.UseCase2
import com.manuelrojas.core.rx.SchedulerProvider
import com.manuelrojas.core.rx.disposeWith
import com.manuelrojas.domain_weather.model.WeatherInfo
import com.manuelrojas.domain_weather.repository.WeatherRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val schedulerProvider: SchedulerProvider
): UseCase2<Result<WeatherInfo>, GetWeatherUseCase.Params>() {

    private lateinit var params: Params

    fun buildUseCaseParams(params: Params) {
        this.params = params
    }

    override fun buildUseCaseObservable(params: Params) =
            weatherRepository.getWeatherForLocation(params.latLon)

    override fun executeUseCase(result: (result: Result<WeatherInfo>) -> Unit) {
        buildUseCaseObservable(params)
                .onErrorReturn(::onError)
                .subscribeOn(schedulerProvider.newThread)
                .observeOn(schedulerProvider.mainThread)
                .subscribe(result)
                .disposeWith(compositeDisposable)
    }

    class Params private constructor(val latLon: String) {

        companion object {
            fun buildParams(location: String): Params {
                return Params(location)
            }
        }

    }

    private fun onError(throwable: Throwable): Result<WeatherInfo> {
        return when (throwable) {
            is SocketTimeoutException -> Failure(HttpError(Throwable(throwable.message)))
            is UnknownHostException -> Failure(HttpError(Throwable(throwable.message)))
            is ConnectException -> Failure(HttpError(Throwable(throwable.message)))
            else -> {
                Failure(HttpError(Throwable(throwable.message)))
            }
        }
    }

}