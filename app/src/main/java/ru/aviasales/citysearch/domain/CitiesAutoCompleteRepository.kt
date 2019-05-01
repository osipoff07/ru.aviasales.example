package ru.aviasales.citysearch.domain

import android.support.annotation.WorkerThread
import ru.aviasales.common.Response
import ru.aviasales.common.domain.model.City

interface CitiesAutoCompleteRepository {

    @WorkerThread
    fun getCities(
        term: String,
        lang: String
    ): Response<List<City>>
}