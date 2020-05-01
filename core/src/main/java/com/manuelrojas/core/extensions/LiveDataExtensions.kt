package com.manuelrojas.core.extensions

import androidx.lifecycle.MutableLiveData
import com.manuelrojas.core.resource.Resource

fun<T> MutableLiveData<Resource<T>>.setSuccess(data: T) =
        postValue(Resource(Resource.State.Success, data, null))

fun<T> MutableLiveData<Resource<T>>.setError(message: String? = null) =
        postValue(Resource(Resource.State.Error, value?.data, message))

fun<T> MutableLiveData<Resource<T>>.setLoading() =
        postValue(Resource(Resource.State.Loading, null, null))

//fun<T> MutableLiveData<Resource<T>>.setSuccess(data: T) =
//        postValue(Resource(Status.SUCCESS, data, null))
//
//fun<T> MutableLiveData<Resource<T>>.setError(message: String? = null) =
//        postValue(Resource(Status.ERROR, value?.data, message))
