package ru.aviasales.citysearch.domain

import ru.aviasales.common.Response
import ru.aviasales.common.domain.UserLanguageRepository
import ru.aviasales.common.domain.model.City

class CitySearchInteractor(
    private val citiesAutoCompleteRepository: CitiesAutoCompleteRepository,
    private val popularCitiesRepository: PopularCitiesRepository,
    private val locationRepository: CityLocationRepository,
    private val languageRepository: UserLanguageRepository
): CitySearchContract.InputPost {

    private var outputPort: CitySearchContract.OutputPort = EmptyOutputPort

    override fun attachOutputPort(
        outputPort: CitySearchContract.OutputPort
    ) {
        this.outputPort = outputPort
    }

    override fun detachOutputPort() {
        this.outputPort = EmptyOutputPort
    }

    override fun loadAutoCompleteCities(
        userInput: String
    ) {
        if (userInput == EMPTY_USER_INPUT) {
            onEmptyUserInput()

            return
        }
        val language: String = languageRepository.getIsoLanguageCode()
        val citiesResponse: Response<List<City>> = citiesAutoCompleteRepository.getCities(
            userInput,
            language
        )
        onCitiesLoaded(citiesResponse)
    }

    private fun onEmptyUserInput() {
        val language: String = languageRepository.getIsoLanguageCode()
        val locationCity: Response<City> = locationRepository.getCityLocation(language)
        val popularCities: Response<List<City>> = popularCitiesRepository.loadPopularCities(language)
        if (locationCity is Response.Failed && popularCities is Response.Failed) {
            onCitiesLoaded(popularCities)
        }
        val citiesList: MutableList<City> = mutableListOf()
        if (locationCity is Response.Success) {
            citiesList.add(locationCity.data)
        }
        if (popularCities is Response.Success) {
            citiesList.addAll(popularCities.data)
        }
        showCities(citiesList)
    }

    private fun onCitiesLoaded(
        citiesResponse: Response<List<City>>
    ) = when(citiesResponse) {
        is Response.Success -> showCities(citiesResponse.data)
        is Response.Failed -> outputPort.onCitiesLoadedError(citiesResponse.exception)
    }

    private fun showCities(
        data: List<City>
    ) = outputPort.onCitiesLoaded(data)
}