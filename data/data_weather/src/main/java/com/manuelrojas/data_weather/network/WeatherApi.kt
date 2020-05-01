package com.manuelrojas.data_weather.network

import com.manuelrojas.core.BuildConfig
import com.manuelrojas.data_weather.network.model.WeatherInfoResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://api.darksky.net/"

interface WeatherApi {

    @GET("forecast/{appKey}/{location}")
    fun getWeatherForLocation(@Path("location") location: String,
                              @Path("appKey") appKey: String = BuildConfig.API_KEY_WEATHER
    ): Call<WeatherInfoResponse>

    companion object {
      operator fun invoke(): WeatherApi {
          val okHttpClient = OkHttpClient.Builder().build()

          return Retrofit.Builder()
                  .client(okHttpClient)
                  .baseUrl(BASE_URL)
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                  .addConverterFactory(GsonConverterFactory.create())
                  .build()
                  .create(WeatherApi::class.java)
      }
    }

}