package com.damianopatane.weatherapplication.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damianopatane.weatherapplication.app.Constants.WEAHTER_ICON_URL
import com.damianopatane.weatherapplication.databinding.HourlyWeatherItemBinding
import com.damianopatane.weatherapplication.domain.model.Hourly
import com.damianopatane.weatherapplication.helpers.FieldConverter

class HourlyWeatherViewHolder private constructor(val binding: HourlyWeatherItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Hourly) {
        binding.icon = WEAHTER_ICON_URL + item.weather.first().icon + "@4x.png"
        binding.temp = FieldConverter.getInstance().removeDecimalConversion(item.temp)
        binding.time = item.dt.toString()
    }

    companion object {
        fun from(parent: ViewGroup): HourlyWeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HourlyWeatherItemBinding.inflate(layoutInflater, parent, false)
            return HourlyWeatherViewHolder(binding)
        }
    }
}