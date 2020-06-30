package com.damianopatane.weatherapplication.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.model.CityWeather

@BindingAdapter(value = ["android:cities"])
fun bindCityAdapter(recyclerView: RecyclerView, items: List<CityItem>?) {
    val adapter = recyclerView.adapter
    items?.let { if (adapter is CityListAdapter) adapter.setData(it) }
}

@BindingAdapter(value = ["android:hourly_weather"])
fun bindHourlyWeatherAdapter(recyclerView: RecyclerView, items: CityWeather?) {
    val adapter = recyclerView.adapter
    items?.let { if (adapter is HourlyWeatherAdapter) adapter.setData(it) }
}

@BindingAdapter(value = ["android:daily_weather"])
fun bindWeatherAdapter(recyclerView: RecyclerView, items: CityWeather?) {
    val adapter = recyclerView.adapter
    items?.let { if (adapter is DailyWeatherAdapter) adapter.setData(it) }
}

@BindingAdapter("onRefresh")
inline fun SwipeRefreshLayout.setOnRefreshListener(crossinline onRefresh: () -> Unit) {
    setOnRefreshListener { onRefresh() }
}

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}