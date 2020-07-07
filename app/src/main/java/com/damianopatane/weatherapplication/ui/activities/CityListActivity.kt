package com.damianopatane.weatherapplication.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.damianopatane.weatherapplication.R
import com.damianopatane.weatherapplication.databinding.ActivityCitiesBinding
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.ui.adapters.CityItemListener
import com.damianopatane.weatherapplication.ui.adapters.CityListAdapter
import com.damianopatane.weatherapplication.ui.viewmodels.CityListViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_cities.*
import java.io.Serializable
import javax.inject.Inject


class CityListActivity : BaseActivity() {

    private val tag = CityListViewModel::class.java.name
    private lateinit var activityCitiesBinding : ActivityCitiesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : CityListViewModel by lazy  {
        ViewModelProvider(this, viewModelFactory).get(CityListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        activityCitiesBinding = DataBindingUtil.setContentView(this, R.layout.activity_cities)
        activityCitiesBinding.cityListViewModel = viewModel

        val cityListAdapter = CityListAdapter(CityItemListener { item ->
            activityCitiesBinding.cityListViewModel?.onCityItemClicked(item)
        })

        initRecycler(cityListAdapter)
        initObservers()

        supportActionBar?.displayOptions = DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.actionbar)
    }

    private fun initRecycler(cityListAdapter : CityListAdapter) {
        city_recyclerview.run {
            setHasFixedSize(true)
            adapter = cityListAdapter
        }
    }

    // quickfix way to remove splashscreen background from default theme
    override fun getTheme(): Resources.Theme {
        val theme: Resources.Theme = super.getTheme()
        theme.applyStyle(R.style.NormalTheme, true)
        return theme
    }


    private fun initObservers() {
        activityCitiesBinding.run {
            lifecycleOwner = this@CityListActivity
            // set refreshing animation
            viewModel.isLoading.observe(this@CityListActivity, Observer<Boolean> {
                swipecontainer.isRefreshing = it as Boolean
            })

            // set intent open new activity
            viewModel.navigateWithCityItem().observe(this@CityListActivity, Observer<CityItem> {
                val intent = Intent(
                    this@CityListActivity,
                    CityWeatherActivity::class.java
                ).putExtra("CityItem", (it as CityItem) as Serializable)
                startActivity(intent)
            })
        }
    }
}
