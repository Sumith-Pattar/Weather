package com.org.weather.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coordinate(@SerializedName("lon")
                      val longitude:Double,
                      @SerializedName("lat")
                      val latitude:Double):Serializable
