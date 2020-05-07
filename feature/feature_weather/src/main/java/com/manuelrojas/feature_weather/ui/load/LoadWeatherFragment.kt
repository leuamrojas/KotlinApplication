package com.manuelrojas.feature_weather.ui.load

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import com.manuelrojas.core.common.WeatherUtils
import com.manuelrojas.core.resource.Resource
import com.manuelrojas.feature_weather.R
import com.manuelrojas.feature_weather.common.*
import com.manuelrojas.feature_weather.model.WeatherView
import com.manuelrojas.feature_weather.ui.base.BaseFragment
import kotlinx.android.synthetic.main.load_weather_fragment.*

const val AUTOCOMPLETE_REQUEST_CODE = 1
private val TAG = LoadWeatherFragment::class.java.name

class LoadWeatherFragment : BaseFragment() {

    private lateinit var vm: LoadWeatherViewModel

    private val placeFields: List<Place.Field> = listOf(
            Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

    private var location: String? = null

    private var isSwipeRefresh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this, vmFactory)[LoadWeatherViewModel::class.java]
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.load_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setSwipeRefreshView()
        if (savedInstanceState != null) {
            restoreWeatherState(savedInstanceState)
        } else {
            checkFirstLoad()
        }
        initializeViewModelObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weather, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.action_search_city2 -> {
                onSearchCityClicked()
                true
            }
//            R.id.action_my_location2 -> {
//                Toast.makeText(activity!!.applicationContext, "My location", Toast.LENGTH_SHORT).show()
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveWeatherState(outState)
    }

    private fun saveWeatherState(outState: Bundle) {
        outState.putString(WEATHER_CONDITION, tv_condition.text as String?)
        outState.putString(WEATHER_TEMPERATURE, tv_temperature.text as String?)
        outState.putString(WEATHER_FEELS_LIKE, tv_feels_like_temperature.text as String?)
        outState.putString(WEATHER_WIND, tv_wind.text as String?)
        outState.putString(WEATHER_VISIBILITY, tv_visibility.text as String?)
        outState.putString(WEATHER_HUMIDITY, tv_humidity.text as String?)
        outState.putString(WEATHER_PRECIPITATION, tv_precipitation.text as String?)
        outState.putString(WEATHER_ICON, weather_icon.text as String?)
        outState.putString(WEATHER_LAST_UPDATE, tv_last_update.text as String?)
        outState.putString(WEATHER_CITY, (activity as? AppCompatActivity)?.supportActionBar?.title as String?)
        outState.putString(WEATHER_LOCATION, location)
    }

    private fun restoreWeatherState(outState: Bundle){
        tv_condition.text = outState.getString(WEATHER_CONDITION)
        tv_temperature.text = outState.getString(WEATHER_TEMPERATURE)
        tv_feels_like_temperature.text = outState.getString(WEATHER_FEELS_LIKE)
        tv_wind.text = outState.getString(WEATHER_WIND)
        tv_visibility.text = outState.getString(WEATHER_VISIBILITY)
        tv_humidity.text = outState.getString(WEATHER_HUMIDITY)
        tv_precipitation.text = outState.getString(WEATHER_PRECIPITATION)
        weather_icon.text = outState.getString(WEATHER_ICON)
        tv_last_update.text = outState.getString(WEATHER_LAST_UPDATE)
        (activity as? AppCompatActivity)?.supportActionBar?.title = outState.getString(WEATHER_CITY)
        location = outState.getString(WEATHER_LOCATION)
    }

    private fun setSwipeRefreshView() {
        swipeRefresh.setOnRefreshListener {
            isSwipeRefresh = true
            if (location != null)
                loadWeather()
            else
                swipeRefresh.isRefreshing = false
        }
    }

    private fun checkFirstLoad() {
        location = preferenceUtils.getLastLocation()
        if (location != null)
            loadWeather()
    }

    private fun onSearchCityClicked() {
        val autocompleteIntent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields)
                .build(activity!!)
        startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && data != null) {
                    when(resultCode) {
                            AutocompleteActivity.RESULT_OK -> {
                                val place = Autocomplete.getPlaceFromIntent(data)
                                val lat = place.latLng?.latitude.toString()
                                val lon = place.latLng?.longitude.toString()
                                location = getLocation(
                                        place.latLng?.latitude.toString(),
                                        place.latLng?.longitude.toString()
                                )
                                Log.d(TAG, "Place: ${place.name}, ${place.id}, ${place.address}, $lat, $lon")
                                loadWeather()
                            }
                            AutocompleteActivity.RESULT_ERROR -> {
                                val status = Autocomplete.getStatusFromIntent(data)
                                Log.d(TAG, "Status: ${status.statusMessage}")
                            }
                            AutocompleteActivity.RESULT_CANCELED -> {
                                // The user canceled the operation.
                            }
                    }

        }

    }

    private fun getLocation(lat: String, lon: String) = "$lat,$lon"

    private fun loadWeather() {
        vm.getWeatherData(location!!)
    }

    private fun initializeViewModelObserver() {
       vm.getWeatherLiveData.observe(viewLifecycleOwner, Observer(::weatherReceived))
    }

    private fun weatherReceived(weatherResource: Resource<WeatherView>) {
        weatherResource.let {
            when (it.state) {
                is Resource.State.Loading -> onWeatherFetchLoading()
                is Resource.State.Success -> onWeatherFetchSuccess(it)
                is Resource.State.Error -> onWeatherFetchError(it)
            }
        }
    }

    private fun onWeatherFetchLoading() {
        if ( !isSwipeRefresh ) group_loading.visibility = View.VISIBLE
    }

    private fun onWeatherFetchError(weatherResource: Resource<WeatherView>) {
        if (!isSwipeRefresh) {
            group_loading.visibility = View.GONE
        } else {
            swipeRefresh.isRefreshing = false
            isSwipeRefresh = false
        }
        when (weatherResource.message) {
            NO_INTERNET_ERROR -> setConnectionError(getString(R.string.no_internet_connection))
            else -> setConnectionError(getString(R.string.connection_error))
        }
    }

    private fun setConnectionError(message: String) =
        Snackbar.make(loadWeatherFragmentContainer, message, Snackbar.LENGTH_SHORT).show()

    private fun onWeatherFetchSuccess(weatherResource: Resource<WeatherView>) {
        if (!isSwipeRefresh)
            group_loading.visibility = View.GONE
        else {
            swipeRefresh.isRefreshing = false
            isSwipeRefresh = false
        }

        displayWeatherView(weatherResource.data!!)
        preferenceUtils.saveLastLocation(location)
    }

    private fun displayWeatherView(weatherView: WeatherView) {
        tv_condition.text = weatherView.summary
        tv_temperature.text = WeatherUtils.getWeatherTemperature(
                weatherView.temperature, convert = true, toCelsius = true)
        tv_feels_like_temperature.text = WeatherUtils.getWeatherFeelsLike(
                getString(R.string.title_feels_like), weatherView.apparentTemperature)
        tv_wind.text = WeatherUtils.getWeatherWindSpeed(
                getString(R.string.title_wind), weatherView.windSpeed)
        tv_visibility.text = WeatherUtils.getWeatherVisibility(
                getString(R.string.title_visibility), weatherView.visibility)
        tv_humidity.text = WeatherUtils.getWeatherHumidity(
                getString(R.string.title_humidity), weatherView.humidity)
        tv_precipitation.text = WeatherUtils.getWeatherPrecipProbability(
                getString(R.string.title_precipitation), weatherView.precipProbability)
        weather_icon.setIconResource(getWeatherIcon(weatherView.icon))
        tv_last_update.text =
                WeatherUtils.getLastUpdateDateTime(getString(R.string.title_last_update), weatherView.time)

        (activity as? AppCompatActivity)?.supportActionBar?.title = weatherView.city
    }

    private fun getWeatherIcon(icon: String) : String{

        return when(icon) {

            CLEAR_DAY -> getString(R.string.wi_day_sunny)
            CLEAR_NIGHT -> getString(R.string.wi_night_clear)
            RAIN -> getString(R.string.wi_rain)
            SNOW -> getString(R.string.wi_snow)
            SLEET -> getString(R.string.wi_sleet)
            WIND -> getString(R.string.wi_windy)
            FOG -> getString(R.string.wi_fog)
            CLOUDY -> getString(R.string.wi_cloudy)
            PARTLY_CLOUDY_DAY -> getString(R.string.wi_forecast_io_partly_cloudy_day)
            PARTLY_CLOUDY_NIGHT -> getString(R.string.wi_forecast_io_partly_cloudy_night)
            HAIL -> getString(R.string.wi_hail)
            THUNDERSTORM -> getString(R.string.wi_thunderstorm)
            TORNADO -> getString(R.string.wi_tornado)
            else -> getString(R.string.wi_na)
        }
    }

}