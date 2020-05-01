package com.manuelrojas.data_weather.repository

import com.manuelrojas.core.Failure
import com.manuelrojas.core.HttpError
import com.manuelrojas.core.Result
import com.manuelrojas.core.Success
import com.manuelrojas.core.rx.SchedulerProvider
import com.manuelrojas.data_weather.database.DB_ENTRY_ERROR
import com.manuelrojas.data_weather.database.dao.WeatherDao
import com.manuelrojas.data_weather.database.model.WeatherEntity
import com.manuelrojas.data_weather.network.WeatherApi
import com.manuelrojas.data_weather.network.base.getData
import com.manuelrojas.domain_weather.model.WeatherInfo
import com.manuelrojas.domain_weather.repository.WeatherRepository
import io.reactivex.Observable
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor (
        private val weatherApi: WeatherApi,
        private val weatherDao: WeatherDao,
        private val schedulerProvider: SchedulerProvider
) : BaseRepository<WeatherInfo, WeatherEntity>(), WeatherRepository {

    override fun getWeatherForLocation(location: String): Observable<Result<WeatherInfo>> {

        return fetchData(
                getApiDataProviderObservable(location),
                getDbDataProviderObservable()
        )
    }

    private fun getApiDataProviderObservable(location: String): Observable<Result<WeatherInfo>> {
        return Observable.fromCallable {
                    weatherApi.getWeatherForLocation(location).execute()
                            .getData(
                                    fetchFromCacheAction = { weatherDao.getWeatherInfoForCity(location) },
                                    cacheAction = { weatherDao.saveWeatherInfo(it) }
                            )
                }
                .subscribeOn(schedulerProvider.io)
    }

    private fun getDbDataProviderObservable(): Observable<Result<WeatherInfo>> {
        return Observable.create<Result<WeatherInfo>> {
                    val dbResult = weatherDao.getWeatherInfo()
                    if (dbResult != null)
                        it.onNext( Success(dbResult.mapToDomainModel()) )
                    else
                        it.onNext( Failure(HttpError(Throwable(DB_ENTRY_ERROR))) )
                }
                .subscribeOn(schedulerProvider.io)
    }

}