package com.org.weather.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "location_weather_table")
data class LocationWeather(
    @Embedded
    @SerializedName("city")
    val city: City,
    @SerializedName("time")
    val time: Long,
    @Embedded
    @SerializedName("main")
    val main: Main,
    @Embedded
    @SerializedName("wind")
    val wind: Wind,
    @Embedded
    @SerializedName("clouds")
    val clouds: Clouds
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}
