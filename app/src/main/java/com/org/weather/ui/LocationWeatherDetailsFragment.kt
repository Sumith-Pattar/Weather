package com.org.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.org.weather.R
import com.org.weather.model.LocationWeather
import com.org.weather.utils.*

class LocationWeatherDetailsFragment : Fragment() {

    private lateinit var locationWeather: LocationWeather
    private lateinit var locationTextView: TextView
    private lateinit var dateTimeTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var humidityValueTextView: TextView
    private lateinit var pressureValueTextView: TextView
    private lateinit var windValueTextView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.location_detail, container, false)
        initUi(view)
        if (arguments != null && requireArguments().containsKey(AppConstants.SELECTED_LOCATION_KEY)) {
            locationWeather =
                arguments?.getSerializable(AppConstants.SELECTED_LOCATION_KEY) as LocationWeather
            showWeatherDetails()
        }
        return view
    }

    private fun initUi(view: View) {
        locationTextView = view.findViewById(R.id.cityNameTextView)
        dateTimeTextView = view.findViewById(R.id.dateTimeTextView)
        temperatureTextView = view.findViewById(R.id.temperatureTextView)
        humidityValueTextView = view.findViewById(R.id.humidityValueTextView)
        pressureValueTextView = view.findViewById(R.id.pressureValueTextView)
        windValueTextView = view.findViewById(R.id.windSpeedValueTextView)
    }

    private fun showWeatherDetails() {

        val cityName = locationWeather.city.name
        val countryName = locationWeather.city.country
        val time: Long = locationWeather.time
        val temperature = locationWeather.main.temp
        val pressure = locationWeather.main.pressure
        val humidity = locationWeather.main.humidity
        val windSpeed = locationWeather.wind.speed

        locationTextView.text = cityName.plus(", $countryName")
        dateTimeTextView.text = time.unixTimeDateStamp()
        temperatureTextView.text = temperature.kelvinToCelsius()
        humidityValueTextView.text = humidity.humidityPercentage()
        pressureValueTextView.text = pressure.pressureUnit()
        windValueTextView.text = windSpeed.windSpeedUnit()

    }
}