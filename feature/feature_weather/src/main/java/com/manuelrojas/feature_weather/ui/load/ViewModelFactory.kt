package com.manuelrojas.feature_weather.ui.load

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
        private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val provider = viewModels[modelClass]
                ?: viewModels.entries.first { modelClass.isAssignableFrom(it.key) }.value

        return provider.get() as T
    }
}