package com.techdoctorbd.weatherforecastusingmvp.features.weather_info_show.data_class

import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("speed")
        val speed: Float = 0f,
        @SerializedName("deg")
        val deg : Float = 0f
)