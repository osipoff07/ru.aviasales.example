package ru.aviasales.citysearch.domain

import android.support.annotation.WorkerThread
import ru.aviasales.common.Response
import ru.aviasales.common.domain.model.City

interface CityLocationRepository {

    @WorkerThread
    fun getCityLocation(lang: String): Response<City>
}