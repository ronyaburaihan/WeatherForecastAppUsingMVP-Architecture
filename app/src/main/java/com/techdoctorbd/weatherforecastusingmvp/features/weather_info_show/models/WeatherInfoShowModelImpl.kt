package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.models

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.techdoctorbd.weatherforecastusingmvp.common.RequestCompleteListener
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.City
import com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class.WeatherInfoResponse
import com.techdoctorbd.weatherforecastusingmvp.network.ApiInterface
import com.techdoctorbd.weatherforecastusingmvp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import java.util.*

class WeatherInfoShowModelImpl(private val context: Context) : WeatherInfoShowModel {

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()

            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList)

        } catch (exception: Exception) {
            exception.printStackTrace()
            callback.onRequestFailed(exception.localizedMessage!!)
        }

    }

    override fun getWeatherInformation(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call = apiInterface.callApiForWeatherInfo(cityId)

        call.enqueue(object : retrofit2.Callback<WeatherInfoResponse> {
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null) {
                    callback.onRequestSuccess(response.body()!!)
                } else {
                    callback.onRequestFailed(response.message())
                }
            }

            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }

        })
    }
}