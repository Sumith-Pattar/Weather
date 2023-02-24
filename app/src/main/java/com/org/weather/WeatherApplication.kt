package com.org.weather

import android.app.Application
import android.content.Context
import com.org.weather.repository.LocationWeatherRepository
import com.org.weather.room.LocationWeatherDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WeatherApplication : Application() {

    init {
         instance = this
    }

    companion object {
        private var instance:WeatherApplication?=null

        fun applicationContext(): Context? {
            return instance!!.applicationContext
        }
    }
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { LocationWeatherDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { LocationWeatherRepository(database.getLocationWeatherDao()) }

    override fun onCreate() {
        super.onCreate()
    }

}