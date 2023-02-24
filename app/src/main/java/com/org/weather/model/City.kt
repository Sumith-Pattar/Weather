package com.org.weather.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(@SerializedName("id")
                val idCity:Int,
                @SerializedName("name")
                val name:String,
                @SerializedName("findname")
                val findName:String,
                @SerializedName("country")
                val country:String,
                @Embedded
                @SerializedName("coord")
                val coordinate: Coordinate,
                @SerializedName("zoom")
                val zoom:Int
                ):Serializable
