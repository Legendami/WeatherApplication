package com.damianopatane.weatherapplication.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.model.CityWeather
import com.damianopatane.weatherapplication.domain.model.Hourly
import com.damianopatane.weatherapplication.helpers.FieldConverter
import com.damianopatane.weatherapplication.ui.viewholder.HourlyWeatherViewHolder


class HourlyWeatherAdapter() : ListAdapter<List<CityItem>,
        HourlyWeatherViewHolder>(HourlyWeatherDiffCallback()) {

    private var items: ArrayList<Hourly> = arrayListOf()

    fun setData(item: CityWeather?) {
        if (item?.hourly != null)
        {
            items = ArrayList(FieldConverter.getInstance().sortByHour(item.hourly))
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        return HourlyWeatherViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderNameHourly: HourlyWeatherViewHolder, position: Int) {
        if (items.size > position) {
            holderNameHourly.bind(items[position])
        }
    }

    override fun getItemCount() = items.size
}

class HourlyWeatherDiffCallback : DiffUtil.ItemCallback<List<CityItem>>() {
    override fun areItemsTheSame(oldItems: List<CityItem>, newItems: List<CityItem>): Boolean {
        return oldItems[1].cityWeather.lat == newItems[1].cityWeather.lat
    }

    override fun areContentsTheSame(
        oldItems: List<CityItem>,
        newItems: List<CityItem>
    ): Boolean {
        return oldItems[1].cityWeather.hourly[0].temp == newItems[1].cityWeather.hourly[0].temp
    }
}