package ru.aviasales.citysearch.presentation

import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.City

private const val EMPTY_IATA: String = ""

class CitySearchMapper: Mapper<City, CityModel> {

    override fun map(
        from: City
    ): CityModel = CityModel(
        id = from.regionData.id,
        cityName = from.regionData.name,
        fullName = from.fullName,
        iata = from.iata.firstOrNull() ?: EMPTY_IATA
    )
}