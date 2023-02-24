package com.org.weather.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Main(@SerializedName("temp")
                val temp:Double,
                @SerializedName("pressure")
                val pressure:Double,
                @SerializedName("humidity")
                val humidity:Int,
                @SerializedName("temp_min")
                val tempMin: Double,
                @SerializedName("temp_max")
                val tempMax:Double):Serializable
