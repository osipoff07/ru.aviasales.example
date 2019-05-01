package ru.aviasales.citysearch.domain

import android.support.annotation.WorkerThread
import ru.aviasales.common.domain.BaseInputPort
import ru.aviasales.common.domain.model.City
import java.lang.Exception

const val EMPTY_USER_INPUT: String = ""

interface CitySearchContract {

    interface OutputPort {
        @WorkerThread
        fun onCitiesLoaded(data: List<City>)

        @WorkerThread
        fun onCitiesLoadedError(exception: Exception)
    }

    interface InputPost: BaseInputPort<OutputPort> {

        @WorkerThread
        fun loadAutoCompleteCities(userInput: String)
    }
}