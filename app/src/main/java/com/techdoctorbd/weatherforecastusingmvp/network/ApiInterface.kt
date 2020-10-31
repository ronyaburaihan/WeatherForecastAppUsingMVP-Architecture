package com.techdoctorbd.weatherforecastusingmvp.network

import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("id") cityId: Int): Call<WeatherInfoResponse>
}