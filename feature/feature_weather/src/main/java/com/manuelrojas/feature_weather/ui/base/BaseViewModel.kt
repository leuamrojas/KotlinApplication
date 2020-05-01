package com.manuelrojas.feature_weather.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manuelrojas.core.common.Connectivity
import com.manuelrojas.core.resource.Resource
import javax.inject.Inject

abstract class BaseViewModel<T : Any> : ViewModel() {
  
  @Inject
  lateinit var connectivity: Connectivity

  protected val _getWeatherLiveData = MutableLiveData<Resource<T>>()

  val getWeatherLiveData: LiveData<Resource<T>>
    get() = _getWeatherLiveData
  
}