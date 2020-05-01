package com.manuelrojas.data_weather.di

import android.content.Context
import androidx.room.Room
import com.manuelrojas.data_weather.database.WeatherDatabase
import com.manuelrojas.data_weather.database.dao.WeatherDao
import dagger.Module
import dagger.Provides

private const val WEATHER_DB = "weather-database"

@Module
class DatabaseModule {

    @Provides
    @DatabaseScope
    fun provideWeatherDatabase(context: Context): WeatherDatabase {
        //TODO remove fallbackToDestructiveMigration when this goes to production
        return Room.databaseBuilder(context, WeatherDatabase::class.java, WEATHER_DB).fallbackToDestructiveMigration().build()
    }

    @Provides
    @DatabaseScope
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }

}