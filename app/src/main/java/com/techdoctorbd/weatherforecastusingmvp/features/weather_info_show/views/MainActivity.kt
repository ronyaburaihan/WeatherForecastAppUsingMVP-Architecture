package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.techdoctorbd.weatherforecastusingmvp.R
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.City
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherDataModel
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.models.WeatherInfoShowModel
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.models.WeatherInfoShowModelImpl
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.presenters.WeatherInfoShowPresenter
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.presenters.WeatherInfoShowPresenterImpl
import com.techdoctorbd.weatherforecastusingmvp.utils.convertToListOfCityName
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_input_part.*
import kotlinx.android.synthetic.main.layout_sunrise_sunset.*
import kotlinx.android.synthetic.main.layout_weather_additional_info.*
import kotlinx.android.synthetic.main.layout_weather_basic_info.*

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var model: WeatherInfoShowModel
    private lateinit var presenter: WeatherInfoShowPresenter

    private var cityList: MutableList<City> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize model and presenter
        model = WeatherInfoShowModelImpl(applicationContext)
        presenter = WeatherInfoShowPresenterImpl(this, model)

        presenter.fetchCityList()

        btn_view_weather.setOnClickListener {
            val spinnerSelectedItemPosition = spinner.selectedItemPosition
            presenter.fetchWeatherInfo(cityList[spinnerSelectedItemPosition].id)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun handleProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }

    override fun onCityListFetchSuccess(cityList: MutableList<City>) {
        this.cityList = cityList

        val arrayAdapter = ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                cityList.convertToListOfCityName()
        )

        spinner.adapter = arrayAdapter
    }

    override fun onCityListFetchFailure(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE

        tv_date_time?.text = weatherDataModel.dateTime
        tv_temperature?.text = weatherDataModel.temperature
        tv_city_country?.text = weatherDataModel.cityAndCountry
        Glide.with(this).load(weatherDataModel.weatherConditionIconUrl).into(iv_weather_condition)
        tv_weather_condition?.text = weatherDataModel.weatherConditionIconDescription

        tv_humidity_value?.text = weatherDataModel.humidity
        tv_pressure_value?.text = weatherDataModel.pressure
        tv_visibility_value?.text = weatherDataModel.visibility

        tv_sunrise_time?.text = weatherDataModel.sunrise
        tv_sunset_time?.text = weatherDataModel.sunset

    }

    override fun onWeatherInfoFetchFailure(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}