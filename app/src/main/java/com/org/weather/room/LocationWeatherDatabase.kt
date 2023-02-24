package com.org.weather.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.org.weather.model.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [LocationWeather::class], version = 1)
abstract class LocationWeatherDatabase : RoomDatabase() {

    abstract fun getLocationWeatherDao(): LocationWeatherDao

//Singleton to create database object from RoomDatabase class

    companion object {
        @Volatile
        private var INSTANCE: LocationWeatherDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocationWeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationWeatherDatabase::class.java,
                    "location_weather"
                ).build()
                Log.d("TestDb", "Db created")
                INSTANCE = instance
                instance
            }
        }
    }
}