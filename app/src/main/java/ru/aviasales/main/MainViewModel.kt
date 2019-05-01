package ru.aviasales.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.aviasales.citysearch.router.CityResult
import ru.aviasales.citysearch.router.SearchStrategy
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.presentation.CoroutineScopeViewModel
import ru.aviasales.common.presentation.Event
import ru.aviasales.ticketssearch.router.TicketsSearchModel

class MainViewModel(
    private val presentationCityDataMapper: Mapper<City, CityModel>
): CoroutineScopeViewModel() {

    private var fromCity: City? = null
    private var toCity: City? = null

    private val fromCityLiveData: MutableLiveData<CityModel> = MutableLiveData()
    private val toCityLiveData: MutableLiveData<CityModel> = MutableLiveData()
    private val navigationLivaData: MutableLiveData<Event<TicketsSearchModel>> = MutableLiveData()

    fun getFromCityLiveData(): LiveData<CityModel> = fromCityLiveData

    fun getToCityLiveData(): LiveData<CityModel> = toCityLiveData

    fun getNavigationLiveData(): LiveData<Event<TicketsSearchModel>> = navigationLivaData

    fun onCitySelected(
        result: CityResult
    ) {
        val cityModel: CityModel = presentationCityDataMapper.map(result.city)
        when(result.strategy) {
            is SearchStrategy.From -> {
                fromCity = result.city
                fromCityLiveData.postValue(cityModel)
            }
            is SearchStrategy.To -> {
                toCity = result.city
                toCityLiveData.postValue(cityModel)
            }
        }
    }

    fun onCitySearchClicked() {
        val from: City = fromCity ?: return
        val to: City = toCity ?: return

        navigationLivaData.postValue(Event(TicketsSearchModel(from, to)))
    }
}