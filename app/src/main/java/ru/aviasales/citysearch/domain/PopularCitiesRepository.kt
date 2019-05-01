package ru.aviasales.citysearch.domain

import android.support.annotation.WorkerThread
import ru.aviasales.common.Response
import ru.aviasales.common.domain.model.City

interface PopularCitiesRepository {

    @WorkerThread
    fun loadPopularCities(lang: String): Response<List<City>>
}