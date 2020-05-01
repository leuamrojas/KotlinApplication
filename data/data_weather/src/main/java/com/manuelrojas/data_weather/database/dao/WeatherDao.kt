package com.manuelrojas.data_weather.database.dao

import androidx.room.*
import com.manuelrojas.data_weather.database.WEATHER_TABLE_NAME
import com.manuelrojas.data_weather.database.model.WeatherEntity

@Dao
interface WeatherDao {
  
  @Transaction
  fun updateWeatherAndReturn(weather: WeatherEntity): WeatherEntity {
    saveWeatherInfo(weather)
    return getWeatherInfoForCity(weather.city?:"")
  }
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveWeatherInfo(weather: WeatherEntity)
  
  @Query("SELECT * FROM $WEATHER_TABLE_NAME WHERE city = :city LIMIT 1")
  fun getWeatherInfoForCity(city: String): WeatherEntity

  @Query("SELECT * FROM $WEATHER_TABLE_NAME LIMIT 1")
  fun getWeatherInfo(): WeatherEntity
}