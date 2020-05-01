package com.manuelrojas.data_weather.network.model

import com.manuelrojas.data_weather.database.model.WeatherEntity
import com.manuelrojas.data_weather.network.base.DomainMapper
import com.manuelrojas.data_weather.network.base.RoomMapper
import com.manuelrojas.domain_weather.model.WeatherInfo

data class WeatherInfoResponse( val id: Int? = 0,
                                val timezone: String? = "",
                                val currently: Currently
) : RoomMapper<WeatherEntity>, DomainMapper<WeatherInfo> {

  private var city: String? = ""
    get() = timezone?.substring(timezone.indexOf('/')+1)?.replace('_', ' ')

  override fun mapToRoomEntity() = WeatherEntity( city,
                                                  currently.time,
                                                  currently.summary,
                                                  currently.icon,
                                                  currently.nearestStormDistance,
                                                  currently.nearestStormBearing,
                                                  currently.precipIntensity,
                                                  currently.precipProbability,
                                                  currently.temperature,
                                                  currently.apparentTemperature,
                                                  currently.dewPoint,
                                                  currently.humidity,
                                                  currently.pressure,
                                                  currently.windSpeed,
                                                  currently.windGust,
                                                  currently.windBearing,
                                                  currently.cloudCover,
                                                  currently.uvIndex,
                                                  currently.visibility,
                                                  currently.ozone )

  override fun mapToDomainModel() = WeatherInfo(
          city?:"",
          currently.time,
          currently.summary,
          currently.temperature,
          currently.apparentTemperature,
          currently.humidity,
          currently.pressure,
          currently.visibility,
          currently.windSpeed,
          currently.precipProbability,
          currently.icon)
}