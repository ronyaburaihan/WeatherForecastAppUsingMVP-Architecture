package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.views

import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.City
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherDataModel

interface MainActivityView {
    fun handleProgressBarVisibility(visibility: Int)
    fun onCityListFetchSuccess(cityList: MutableList<City>)
    fun onCityListFetchFailure(errorMessage: String)
    fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel)
    fun onWeatherInfoFetchFailure(errorMessage: String)
}