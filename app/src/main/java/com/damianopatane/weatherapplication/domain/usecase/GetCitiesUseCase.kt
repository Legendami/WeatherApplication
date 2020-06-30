package com.damianopatane.weatherapplication.domain.usecase

import com.damianopatane.weatherapplication.domain.model.CityItem
import com.damianopatane.weatherapplication.domain.repository.WeatherDataRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val repository: WeatherDataRepository) {

    suspend fun getCurrentCityEntries(): List<CityItem> {
        return repository.getCurrentCityEntries()
    }

    suspend fun getFullCityWeather(item : CityItem): CityItem {
        return repository.getFullCityEntry(item)
    }
}