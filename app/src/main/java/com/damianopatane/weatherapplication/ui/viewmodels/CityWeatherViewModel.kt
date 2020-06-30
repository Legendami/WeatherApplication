package com.damianopatane.weatherapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.model.CityWeather
import com.damianopatane.weatherapplication.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class CityWeatherViewModel @Inject constructor(private val getCities: GetCitiesUseCase) : BaseObservableViewModel() {
    val isLoad = MediatorLiveData<Boolean>()
    val degree = "°"
    private val cityWeather : MediatorLiveData<CityWeather> = MediatorLiveData()
    private val city : MediatorLiveData<CityItem> = MediatorLiveData()
    private val fullWeather = liveData(Dispatchers.IO) {
        isLoad.postValue(true)
        delay(1000) // remove this in the future
        val fetchCityItems = getCities.getFullCityWeather(city.value!!)
        isLoad.postValue(false)
        emit(fetchCityItems.cityWeather)
    }
    init {
        cityWeather.addSource(fullWeather) { value ->
            cityWeather.value = value
        }
    }

    fun getCityWeather() = cityWeather as LiveData<CityWeather>

    fun getCity() = city as LiveData<CityItem>

    fun setCityItem(item: CityItem) {
        city.postValue(item)
    }
}