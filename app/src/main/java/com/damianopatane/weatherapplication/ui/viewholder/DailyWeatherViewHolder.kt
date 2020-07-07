package com.damianopatane.weatherapplication.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damianopatane.weatherapplication.app.Constants
import com.damianopatane.weatherapplication.databinding.DailyWeatherItemBinding
import com.damianopatane.weatherapplication.domain.model.Daily
import com.damianopatane.weatherapplication.helpers.removeDecimalConversion
import com.damianopatane.weatherapplication.helpers.sortByDay

class DailyWeatherViewHolder private constructor(private val binding: DailyWeatherItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Daily) {
        binding.icon = Constants.WEAHTER_ICON_URL + item.weather.first()?.icon + "@2x.png"
        binding.day = item.temp?.day?.removeDecimalConversion()
        binding.night = item.temp?.night?.removeDecimalConversion()
        binding.weekday = item.sortByDay()
    }

    companion object {
        fun from(parent: ViewGroup): DailyWeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DailyWeatherItemBinding.inflate(layoutInflater, parent, false)
            return DailyWeatherViewHolder(binding)
        }
    }
}