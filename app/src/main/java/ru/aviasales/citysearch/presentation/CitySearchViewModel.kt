package ru.aviasales.citysearch.presentation

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import ru.aviasales.citysearch.domain.CitySearchContract
import ru.aviasales.citysearch.domain.EMPTY_USER_INPUT
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.mapList
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.presentation.CoroutineScopeViewModel
import ru.aviasales.common.presentation.Event
import java.lang.Exception

class CitySearchViewModel(
    private val inputPort: CitySearchContract.InputPost,
    private val cityMapper: Mapper<City, CityModel>
): CoroutineScopeViewModel(), LifecycleObserver, CitySearchContract.OutputPort {

    private var cities: List<City> = listOf()
    private var userInput: String = EMPTY_USER_INPUT
    private val autoCompleteCitiesLiveData: MutableLiveData<List<CityModel>> = MutableLiveData()
    private val loadingExceptionLiveData: MutableLiveData<Exception> = MutableLiveData()
    private val selectedCityLiveData: MutableLiveData<Event<City>> = MutableLiveData()

    init {
        inputPort.attachOutputPort(this)
        loadCities()
    }

    override fun onCleared() {
        super.onCleared()
        inputPort.detachOutputPort()
    }

    fun getAutoCompleteCitiesLiveData(): LiveData<List<CityModel>> = autoCompleteCitiesLiveData

    fun getLoadingExceptionLiveData(): LiveData<Exception> = loadingExceptionLiveData

    fun getSelectedCityLiveDataLiveData(): LiveData<Event<City>> = selectedCityLiveData

    override fun onCitiesLoaded(
        data: List<City>
    ) {
        cities = data
        autoCompleteCitiesLiveData.postValue(cityMapper.mapList(data))
    }

    override fun onCitiesLoadedError(
        exception: Exception
    ) = loadingExceptionLiveData.postValue(exception)

    //Todo Task.No needs to add queue
    fun onUserInputChanged(userInput: String) {
        this.userInput = userInput
        loadCities()
    }

    fun onCitySelected(
        cityModel: CityModel
    ) {
        val selectedCity: City = cities.firstOrNull { it.regionData.id == cityModel.id } ?: return

        selectedCityLiveData.postValue(Event(selectedCity))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        if (isEmptyResult()) {
            loadCities()
        }
    }

    private fun loadCities() {
        launch(ioContext) {
            inputPort.loadAutoCompleteCities(userInput)
        }
    }

    private fun isEmptyResult(): Boolean {
        return !defaultJob.isCompleted && autoCompleteCitiesLiveData.value?.isEmpty() == true
    }
}