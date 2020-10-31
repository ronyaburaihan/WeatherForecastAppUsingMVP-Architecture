package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.presenters

interface WeatherInfoShowPresenter {
    fun fetchCityList()
    fun fetchWeatherInfo(cityId: Int)
    fun detachView()
}