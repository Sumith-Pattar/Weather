package com.org.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.org.weather.model.viewModel.LocationWeatherViewModel
import com.org.weather.model.viewModel.LocationWeatherViewModelFactory
import com.org.weather.ui.LocationWeatherDetailsFragment
import com.org.weather.ui.SearchLocationFragment
import com.org.weather.utils.AppConstants

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(SearchLocationFragment())

        val locationViewModelFactory =
            LocationWeatherViewModelFactory((application as WeatherApplication).repository)
        val locationWeatherViewModel = ViewModelProvider(
            this,
            locationViewModelFactory
        )[LocationWeatherViewModel::class.java]
        locationWeatherViewModel.locationSelected.observe(this) { currentLocation ->
            val bundle = Bundle()

            bundle.putSerializable(AppConstants.SELECTED_LOCATION_KEY, currentLocation)

            val locationWeatherDetailsFragment = LocationWeatherDetailsFragment()
            locationWeatherDetailsFragment.arguments = bundle
            replaceFragment(locationWeatherDetailsFragment)
        }
    }

    /**
     * Method to add fragment to the container
     * @param fragment that has to be added to the container
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, fragment).commit()
    }

    /**
     * Method to replace fragment to the container
     * @param fragment that has to be replaced in  the container
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, fragment)
            .addToBackStack(null).commit()
    }
}