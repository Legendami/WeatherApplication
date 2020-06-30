package com.damianopatane.weatherapplication.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.usecase.GetCitiesUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CityListViewModel @Inject constructor(private val getCities: GetCitiesUseCase): BaseObservableViewModel() {
    val isLoading = MediatorLiveData<Boolean>()
    private val cityItem : MediatorLiveData<CityItem> = MediatorLiveData()
    private val cityItems : MediatorLiveData<List<CityItem>> = MediatorLiveData()
    private val items = liveData(Dispatchers.IO) {
        val fetchCityItems = getCities.getCurrentCityEntries()
        emit(fetchCityItems)
    }

    fun getCityItems() = cityItems as LiveData<List<CityItem>>
    fun navigateWithCityItem() = cityItem as LiveData<CityItem>

    init {
        cityItems.addSource(items) { value ->
            cityItems.setValue(value)
        }
    }

    fun onCityItemClicked(item: CityItem) {
        cityItem.postValue(item)
    }

    fun refreshWeatherItems() {
        viewModelScope.launch {
            cityItems.postValue(getCities.getCurrentCityEntries())
            isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        // dispose possible realm threads in the future, for now it is closed from CityListActivity
    }
}