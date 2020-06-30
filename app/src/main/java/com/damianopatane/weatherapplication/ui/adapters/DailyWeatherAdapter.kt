package com.damianopatane.weatherapplication.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.model.CityWeather
import com.damianopatane.weatherapplication.domain.model.Daily
import com.damianopatane.weatherapplication.ui.viewholder.DailyWeatherViewHolder
import com.damianopatane.weatherapplication.ui.viewholder.HourlyWeatherViewHolder

class DailyWeatherAdapter() : ListAdapter<List<CityItem>,
        DailyWeatherViewHolder>(DailyWeatherDiffCallback()) {

    private var items: ArrayList<Daily> = arrayListOf()

    fun setData(item: CityWeather?) {
        if (item?.daily != null)
        {
            items = ArrayList(item.daily)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        return DailyWeatherViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderNameDaily: DailyWeatherViewHolder, position: Int) {
        if (items.size > position) {
            holderNameDaily.bind(items[position])
        }
    }

    override fun getItemCount() = items.size
}

class DailyWeatherDiffCallback : DiffUtil.ItemCallback<List<CityItem>>() {
    override fun areItemsTheSame(oldItems: List<CityItem>, newItems: List<CityItem>): Boolean {
        return oldItems[1].cityWeather.lat == newItems[1].cityWeather.lat
    }

    override fun areContentsTheSame(
        oldItems: List<CityItem>,
        newItems: List<CityItem>
    ): Boolean {
        return oldItems[1].cityWeather.daily[0].temp == newItems[1].cityWeather.daily[0].temp
    }
}