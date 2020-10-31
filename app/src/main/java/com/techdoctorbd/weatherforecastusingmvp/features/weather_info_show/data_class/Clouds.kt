package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class

import com.google.gson.annotations.SerializedName

data class Clouds(
        @SerializedName("all")
        val all: Int = 0
)