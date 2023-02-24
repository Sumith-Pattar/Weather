package com.org.weather.adapter

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.org.weather.R
import com.org.weather.model.LocationWeather
import com.org.weather.model.viewModel.LocationWeatherViewModel

class LocationWeatherAdapter() :
    RecyclerView.Adapter<LocationWeatherAdapter.LocationWeatherViewHolder>(), Filterable {

    var locationListFiltered: ArrayList<LocationWeather> = ArrayList()
    lateinit var locationFullList: ArrayList<LocationWeather>
    private lateinit var context: Context
    lateinit var locationSharedViewModel: LocationWeatherViewModel

    constructor(locationList: ArrayList<LocationWeather>, context: Context) : this() {
        this.locationListFiltered = locationList
        this.locationFullList = ArrayList(locationList)
        this.context = context

    }

    class LocationWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        val countryTextView: TextView = itemView.findViewById(R.id.countryTextView)
        val locationCardView: CardView = itemView.findViewById(R.id.locationCardView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationWeatherViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
        return LocationWeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LocationWeatherViewHolder,
        position: Int
    ) {
        val currentLocation: LocationWeather = locationListFiltered[position]
        holder.cityTextView.text = currentLocation.city.name
        holder.countryTextView.text = currentLocation.city.country
        holder.locationCardView.setOnClickListener {
            locationSharedViewModel.changeLocation(currentLocation)
        }
    }

    override fun getItemCount(): Int {
        return locationListFiltered.size
    }

    //*set locationList*//*
    @SuppressLint("NotifyDataSetChanged")
    fun set(locationList: ArrayList<LocationWeather>) {
        this.locationFullList = locationList
        this.locationListFiltered = this.locationFullList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return locationFilter
    }


    //*Filter for list view on basis of input search*//*
    private val locationFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredLocationList: ArrayList<LocationWeather> = ArrayList()
            if (constraint == null || constraint.isEmpty()) {
                filteredLocationList = locationFullList
                locationListFiltered = locationFullList
            } else {
                val filterPattern: String = constraint.toString().lowercase().trim()
                locationFullList.filter {
                    (it.city.name.lowercase()
                        .startsWith(filterPattern, 0, true) || (it.city.name.lowercase()
                        .contains(filterPattern)))
                }.forEach { filteredLocationList.add(it) }
            }

            // }
            val results = FilterResults()
            results.values = filteredLocationList

            return results

        }

        override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
            locationListFiltered =
                if (result?.values == null) ArrayList() else result.values as ArrayList<LocationWeather>
            notifyDataSetChanged()
        }

    }

}