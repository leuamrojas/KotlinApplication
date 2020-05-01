package com.manuelrojas.feature_weather.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.manuelrojas.core.BuildConfig
import com.manuelrojas.core.common.PreferenceUtils
import com.manuelrojas.feature_weather.di.WeatherInjector
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    @Inject
    lateinit var preferenceUtils: PreferenceUtils

    lateinit var  placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WeatherInjector.component.inject(this)
        initPlaces()
    }

    private fun initPlaces() {
        Places.initialize(activity!!, BuildConfig.API_KEY_PLACES)
        placesClient = Places.createClient(activity!!)
    }

}