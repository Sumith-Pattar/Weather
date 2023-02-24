package com.org.weather.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind(@SerializedName("speed")
                val speed:Double,
                @SerializedName("deg")
                val deg:Double,
                @SerializedName("var_beg")
                val varBeg:Int,
                @SerializedName("var_end")
                val varEnd:Int):Serializable
