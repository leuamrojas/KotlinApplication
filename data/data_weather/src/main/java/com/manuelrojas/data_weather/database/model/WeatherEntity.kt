package com.manuelrojas.data_weather.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manuelrojas.data_weather.database.WEATHER_TABLE_NAME
import com.manuelrojas.data_weather.network.base.DomainMapper
import com.manuelrojas.domain_weather.model.WeatherInfo

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = WEATHER_TABLE_NAME)
data class WeatherEntity(
                         val city: String? = "",
                         val time: String,
                         val summary: String,
                         val icon: String,
                         val nearestStormDistance: Int,
                         val nearestStormBearing: Int,
                         val precipIntensity: Double,
                         val precipProbability: Double,
                         val temperature: Double,
                         val apparentTemperature: Double,
                         val dewPoint: Double,
                         val humidity: Double,
                         val pressure: Double,
                         val windSpeed: Double,
                         val windGust: Double,
                         val windBearing: Int,
                         val cloudCover: Double,
                         val uvIndex: Int,
                         val visibility: Double,
                         val ozone: Double) : DomainMapper<WeatherInfo> {

    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID

    override fun mapToDomainModel() = WeatherInfo(
            city?:"",
            time,
            summary,
            temperature,
            apparentTemperature,
            humidity,
            pressure,
            visibility,
            windSpeed,
            precipProbability,
            icon)
}
