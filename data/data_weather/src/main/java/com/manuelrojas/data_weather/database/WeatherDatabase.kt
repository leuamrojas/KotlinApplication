package com.manuelrojas.data_weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.manuelrojas.data_weather.database.dao.WeatherDao
import com.manuelrojas.data_weather.database.model.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
  
  abstract fun weatherDao(): WeatherDao
}