package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.presenters

import android.view.View
import com.techdoctorbd.weatherforecastusingmvp.common.RequestCompleteListener
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.City
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherDataModel
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherInfoResponse
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.models.WeatherInfoShowModel
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.views.MainActivityView
import com.techdoctorbd.weatherforecastusingmvp.utils.kelvinToCelsius
import com.techdoctorbd.weatherforecastusingmvp.utils.unixTimestampToDateTimeString
import com.techdoctorbd.weatherforecastusingmvp.utils.unixTimestampToTimeString

class WeatherInfoShowPresenterImpl(
        private var view: MainActivityView?,
        private val model: WeatherInfoShowModel) : WeatherInfoShowPresenter {

    override fun fetchCityList() {
        model.getCityList(object : RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                view?.onCityListFetchSuccess(data) //let view know the formatted city list data
            }

            override fun onRequestFailed(errorMessage: String) {
                view?.onCityListFetchFailure(errorMessage) //let view know about failure
            }

        })
    }

    override fun fetchWeatherInfo(cityId: Int) {
        view?.handleProgressBarVisibility(View.VISIBLE) // let view know about progress bar visibility

        model.getWeatherInformation(cityId, object : RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {
                view?.handleProgressBarVisibility(View.GONE) // let view know about progress bar visibility

                val weatherDataModel = WeatherDataModel(
                        dateTime = data.dt.unixTimestampToDateTimeString(),
                        temperature = data.main.temp.kelvinToCelsius().toString(),
                        cityAndCountry = "${data.name} , ${data.sys.country}",
                        weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                        weatherConditionIconDescription = data.weather[0].description,
                        humidity = "${data.main.humidity}%",
                        pressure = "${data.main.pressure}mBar",
                        visibility = "${data.visibility / 1000.0} KM",
                        sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                        sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                view?.onWeatherInfoFetchSuccess(weatherDataModel) //let view know the formatted weather data

            }

            override fun onRequestFailed(errorMessage: String) {
                view?.handleProgressBarVisibility(View.GONE) // let view know about progress bar visibility

                view?.onWeatherInfoFetchFailure(errorMessage) //let view know about failure
            }

        })
    }

    override fun detachView() {
        view = null
    }

}