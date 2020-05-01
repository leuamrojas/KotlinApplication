package com.manuelrojas.core.common

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import kotlin.math.roundToInt

object WeatherUtils {

    fun getWeatherTemperature(temp: Double, convert: Boolean = false, toCelsius: Boolean = false) : String {
        return if (convert) {
                    if (toCelsius)
                        "${getRounded(convertFahrenheitToCelsius(temp))} °C"
                    else
                        "${getRounded(convertCelsiusToFahrenheit(temp))} °F"
                } else {
                    "${getRounded(temp)} °C"
                }
    }

    fun getWeatherWindSpeed(title: String, wind: Double) = "$title: $wind mph"

    fun getWeatherVisibility(title: String, visibility: Double) = "$title: ${String.format("%.2f",visibility)} mi."

    fun getWeatherHumidity(title: String, humidity: Double) = "$title: ${(humidity*100).roundToInt()}%"

    fun getWeatherPrecipProbability(title: String, prob: Double) = "$title: ${(prob*100).roundToInt()}%"

    fun getWeatherFeelsLike(title: String, temp: Double) =
            "$title: ${getWeatherTemperature(temp, convert = true, toCelsius = true)}"

    fun getLastUpdateDateTime(title: String, dateTimeMillis: String) : String {
        return "$title: ${getDateTime(dateTimeMillis.toLong()*1000)}"
    }

    private fun getDateTime(dateTimeMillis: Long) : String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeMillis), ZoneId.systemDefault())
        return "${dateTime.month} ${dateTime.dayOfMonth}, ${dateTime.year} ${dateTime.toLocalTime()}"
    }

    private fun getRounded(number: Double): Long = number.roundToInt().toLong()

    private fun convertFahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5 / 9

    private fun convertCelsiusToFahrenheit(celsius: Double): Double = celsius * 9 / 5 + 32

}