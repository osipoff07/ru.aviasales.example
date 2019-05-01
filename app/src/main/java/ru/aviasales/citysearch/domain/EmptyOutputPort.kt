package ru.aviasales.citysearch.domain

import ru.aviasales.common.domain.model.City
import java.lang.Exception

object EmptyOutputPort: CitySearchContract.OutputPort {

    override fun onCitiesLoaded(
        data: List<City>
    ) = Unit

    override fun onCitiesLoadedError(
        exception: Exception
    ) = Unit
}