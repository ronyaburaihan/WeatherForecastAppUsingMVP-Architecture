package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.models

import com.techdoctorbd.weatherforecastusingmvp.common.RequestCompleteListener
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.City
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInformation(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}
