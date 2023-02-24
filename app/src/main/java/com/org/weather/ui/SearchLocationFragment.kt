package com.org.weather.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.weather.MainActivity
import com.org.weather.R
import com.org.weather.WeatherApplication
import com.org.weather.adapter.LocationWeatherAdapter
import com.org.weather.model.*
import com.org.weather.model.viewModel.LocationWeatherViewModel
import com.org.weather.model.viewModel.LocationWeatherViewModelFactory
import com.org.weather.repository.LocationWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class SearchLocationFragment : Fragment() {

    private lateinit var locationWeatherRepository: LocationWeatherRepository
    private lateinit var locationWeatherViewModel: LocationWeatherViewModel
    private var locationList: ArrayList<LocationWeather> = ArrayList(0)

    lateinit var locationAdapter: LocationWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_search_location, container, false)
        setHasOptionsMenu(true)
        initViewModel()
        locationAdapter = LocationWeatherAdapter(locationList, activity as MainActivity)
        locationAdapter.locationSharedViewModel = locationWeatherViewModel
        val locationRecyclerView: RecyclerView = view.findViewById(R.id.locationRecyclerView)
        locationRecyclerView.layoutManager = LinearLayoutManager(activity)

        locationRecyclerView.adapter = locationAdapter


        (activity?.application as WeatherApplication).applicationScope.launch(Dispatchers.IO) {
            getLocationList()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user, menu)
        val searchLocation = menu.findItem(R.id.searchLocation)
        val searchView = activity?.let { SearchView(it) }
        searchView?.queryHint = this.resources.getString(R.string.search_location)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                locationAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    locationAdapter.filter.filter(newText)
                }
                return true
            }

        })
        searchLocation.actionView = searchView
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                Toast.makeText(activity, this.resources.getString(R.string.about_info), Toast.LENGTH_SHORT).show()

            }


        }
        return true
    }
    /**
     * Get location list
     */
    private suspend fun getLocationList() {

        val job = GlobalScope.async {

            locationList = (WeatherApplication.applicationContext()
                ?.let { locationWeatherViewModel.getLocationList(it) })!!

        }
        job.await()
        GlobalScope.launch(Dispatchers.Main) { locationAdapter.set(locationList) }
    }

    private fun initViewModel() {
        locationWeatherRepository = (activity?.application as WeatherApplication).repository
        val locationWeatherViewModelFactory =
            LocationWeatherViewModelFactory(locationWeatherRepository)
        locationWeatherViewModel = ViewModelProvider(
            requireActivity(),
            locationWeatherViewModelFactory
        )[LocationWeatherViewModel::class.java]
    }

}