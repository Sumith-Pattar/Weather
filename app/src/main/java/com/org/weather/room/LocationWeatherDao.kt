package com.org.weather.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.org.weather.model.LocationWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationWeatherDao {

    @Query(value = "SELECT *FROM location_weather_table ORDER BY id ASC")
     fun getAllLocation(): Flow<List<LocationWeather>>

    @Insert
   suspend fun addLocation( locationWeatherEntity: LocationWeather)

    @Delete
    suspend fun deleteLocation(locationWeather: LocationWeather)

    @Query("DELETE FROM location_weather_table")
    suspend fun deleteAllLocation()

}