package ru.aviasales.citysearch.data

import ru.aviasales.citysearch.domain.PopularCitiesRepository
import ru.aviasales.common.Response
import ru.aviasales.common.domain.model.City

class DefaultPopularCitiesRepository: PopularCitiesRepository {

    //TODO Task No.
    //HARDCODED: no server part yet
    override fun loadPopularCities(
        lang: String
    ): Response<List<City>> = Response.Success(
        data = listOf()
    )
}