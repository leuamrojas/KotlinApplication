package com.manuelrojas.core.resource

data class Resource<out T>(val state: State, val data: T?, val message: String?) {

    sealed class State {
        object Success: State()
        object Error: State()
        object Loading: State()
    }

}