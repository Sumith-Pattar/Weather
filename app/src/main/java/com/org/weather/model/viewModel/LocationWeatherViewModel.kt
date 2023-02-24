package com.org.weather.model.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.org.weather.model.LocationWeather
import com.org.weather.repository.LocationWeatherRepository
import com.org.weather.utils.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStreamReader

class LocationWeatherViewModel(private val locationWeatherRepository: LocationWeatherRepository) :
    ViewModel() {
    val locationSelected = MutableLiveData<LocationWeather>()
    fun changeLocation(locationWeather: LocationWeather) {
        locationSelected.postValue(locationWeather)
    }


    /**
     * This method getsLocationlist form json file
     * @param context the context
     */

    fun getLocationList(context: Context): ArrayList<LocationWeather>? {
        var cityList: ArrayList<LocationWeather>? = null
        try {
            val fileInputStream = context.assets.open(AppConstants.CITY_WEATHER_TEST_JSON)
            val size = fileInputStream.available()
            val buffer = ByteArray(size)
            fileInputStream.read(buffer)
            fileInputStream.close()
            val jsonData = String(buffer)
            val groupListType = object : TypeToken<ArrayList<LocationWeather>>() {}.type
            val gson = GsonBuilder().create()
            cityList = gson.fromJson(jsonData, groupListType)
            return cityList
        } catch (e: IOException) {
            Log.e("TestD", "Exception: ${e.localizedMessage!!}")
        }
        return null
    }

    @Throws(IOException::class)
    fun readJsonStream(context: Context): ArrayList<LocationWeather>? {
        val fileInputStream = context.assets.open(AppConstants.CITY_WEATHER_TEST_JSON)
        val reader = JsonReader(InputStreamReader(fileInputStream, "UTF-8"))
        val messages: ArrayList<LocationWeather> = ArrayList<LocationWeather>()
        reader.beginArray()
        val gson = GsonBuilder().create()
        while (reader.hasNext()) {
            val message: LocationWeather = gson.fromJson(reader,LocationWeather::class.java)
            messages.add(message)
        }
        reader.endArray()
        reader.close()
        return messages
    }

    //TODO: This methods are Database methods to be used for future Db operations
    //Live Data List of Cities Weather
    val locationWeatherList: LiveData<List<LocationWeather>> =
        locationWeatherRepository.locationWeatherList.asLiveData()

    //save Favorite location to DB
    fun addLocation(locationWeather: LocationWeather) = viewModelScope.launch(Dispatchers.IO) {
        locationWeatherRepository.addLocation(locationWeather)
    }

    fun delete(locationWeather: LocationWeather) = viewModelScope.launch(Dispatchers.IO) {
        locationWeatherRepository.delete(locationWeather)
    }

    fun deleteAllLocationWeather() = viewModelScope.launch(Dispatchers.IO) {
        locationWeatherRepository.deleteAllLocationWeather()
    }
}

class LocationWeatherViewModelFactory(private var locationWeatherRepository: LocationWeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationWeatherViewModel::class.java)) {
            return LocationWeatherViewModel(locationWeatherRepository) as T
        } else {
            throw IllegalArgumentException("Unknown View Model")
        }
    }
}
