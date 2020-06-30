package com.damianopatane.weatherapplication.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damianopatane.weatherapplication.di.ViewModelKey
import com.damianopatane.weatherapplication.di.builder.ViewModelFactory
import com.damianopatane.weatherapplication.ui.viewmodels.CityListViewModel
import com.damianopatane.weatherapplication.ui.viewmodels.CityWeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CityListViewModel::class)
    abstract fun bindCitiesViewModel(cityListViewModel: CityListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityWeatherViewModel::class)
    abstract fun bindWeatherViewModel(cityWeatherViewModel: CityWeatherViewModel): ViewModel
}