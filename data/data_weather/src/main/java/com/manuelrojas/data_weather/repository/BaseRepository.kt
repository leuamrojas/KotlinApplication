package com.manuelrojas.data_weather.repository

import com.manuelrojas.core.Failure
import com.manuelrojas.core.HttpError
import com.manuelrojas.core.Result
import com.manuelrojas.core.common.Connectivity
import com.manuelrojas.data_weather.network.GENERAL_NETWORK_ERROR
import com.manuelrojas.data_weather.network.base.DomainMapper
import io.reactivex.Observable
import javax.inject.Inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> {

  @Inject
  lateinit var connectivity: Connectivity

  /**
   * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
   */
  protected fun fetchData( apiDataProvider: Observable<Result<T>>,
                           dbDataProvider: Observable<Result<T>> ): Observable<Result<T>> {
      return if (connectivity.hasNetworkAccess()) apiDataProvider else dbDataProvider
  }

  /**
   * Use this when communicating only with the api service
   */
  protected fun fetchData(dataProvider: () -> Result<T>): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
        dataProvider()
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }
}