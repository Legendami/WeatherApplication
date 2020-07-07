package com.damianopatane.weatherapplication.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.damianopatane.weatherapplication.app.Constants
import com.damianopatane.weatherapplication.databinding.CityItemBinding
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.helpers.decimalConversion
import com.damianopatane.weatherapplication.ui.adapters.CityItemListener

class CityViewHolder private constructor
    (private val binding: CityItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: CityItemListener, item: CityItem) {
        item.cityWeather?.current?.temp = item.cityWeather?.current?.temp?.decimalConversion()!!
        binding.temp = item.cityWeather?.current?.temp.toString() + "°"
        binding.icon = Constants.WEAHTER_ICON_URL + item.cityWeather?.current?.weather?.first()?.icon + "@2x.png"
        binding.item = item
        binding.clickListener = clickListener
    }
    companion object {
        fun from(parent: ViewGroup): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CityItemBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding)
        }
    }
}