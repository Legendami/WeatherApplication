package com.damianopatane.weatherapplication.ui.activities

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.damianopatane.weatherapplication.R
import com.damianopatane.weatherapplication.app.WeatherApplication
import com.damianopatane.weatherapplication.app.WeatherApplication.Companion.closeRealm
import com.damianopatane.weatherapplication.databinding.ActivityCityWeatherBinding
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.ui.adapters.DailyWeatherAdapter
import com.damianopatane.weatherapplication.ui.adapters.HourlyWeatherAdapter
import com.damianopatane.weatherapplication.ui.viewmodels.CityWeatherViewModel
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_city_weather.*
import javax.inject.Inject

class CityWeatherActivity : DaggerAppCompatActivity() {

    private var city : CityItem? = null

    private val TAG = CityWeatherViewModel::class.java.name
    private lateinit var activityCityWeatherBinding : ActivityCityWeatherBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel : CityWeatherViewModel by lazy  {
        ViewModelProvider(this, viewModelFactory).get(CityWeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        city = intent.getSerializableExtra("CityItem") as CityItem
        val hourlyWeatherAdapter = HourlyWeatherAdapter()
        val dailyWeatherAdapter = DailyWeatherAdapter()

        setTheme(R.style.NormalTheme)
        activityCityWeatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_weather)

        initObservers()
        initHourlyRecycler(hourlyWeatherAdapter)
        initDailyRecycler(dailyWeatherAdapter)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initHourlyRecycler(hourlyWeatherAdapter : HourlyWeatherAdapter) {
        hourly_weather_recyclerview.run {
            setHasFixedSize(true)
            adapter = hourlyWeatherAdapter
        }
    }

    private fun initDailyRecycler(dailyWeatherAdapter : DailyWeatherAdapter) {
        daily_weather_recyclerview.run {
            setHasFixedSize(true)
            adapter = dailyWeatherAdapter
        }
    }

    private fun initObservers() {
        activityCityWeatherBinding.run {
            cityWeatherViewModel = viewModel
            lifecycleOwner = this@CityWeatherActivity
            viewModel.setCityItem(city!!)
            title = ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeRealm()
    }
}