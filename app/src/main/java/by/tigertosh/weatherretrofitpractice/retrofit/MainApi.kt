package by.tigertosh.weatherretrofitpractice.retrofit

import by.tigertosh.weatherretrofitpractice.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String
    ): WeatherData

}