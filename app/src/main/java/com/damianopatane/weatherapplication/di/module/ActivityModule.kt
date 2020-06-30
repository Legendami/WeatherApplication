package com.damianopatane.weatherapplication.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.damianopatane.weatherapplication.ui.activities.CityListActivity
import com.damianopatane.weatherapplication.ui.activities.CityWeatherActivity
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [ViewModelModule::class]
    )
    fun cityWeatherActivity(): CityWeatherActivity

    @ContributesAndroidInjector(
        modules = [ViewModelModule::class]
    )
    fun cityListActivity(): CityListActivity
}