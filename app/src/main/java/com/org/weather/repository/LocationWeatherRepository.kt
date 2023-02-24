package com.org.weather.repository

import androidx.annotation.WorkerThread
import com.org.weather.model.LocationWeather
import com.org.weather.room.LocationWeatherDao
import kotlinx.coroutines.flow.Flow

class LocationWeatherRepository(private val locationWeatherDao: LocationWeatherDao) {

    val locationWeatherList:Flow<List<LocationWeather>> = locationWeatherDao.getAllLocation()

   /* @WorkerThread
    suspend fun getAllLocationWeatherEntity():Flow<List<LocationWeatherEntity>> {
        return locationWeatherDao.getAllLocation()
    }*/

    @WorkerThread
    suspend fun addLocation(locationWeather: LocationWeather) {
        locationWeatherDao.addLocation(locationWeather)
    }

    @WorkerThread
    suspend fun delete(locationWeather: LocationWeather) {
        locationWeatherDao.deleteLocation(locationWeather)
    }

    @WorkerThread
    suspend fun deleteAllLocationWeather() {
        locationWeatherDao.deleteAllLocation()
    }

}