package ru.aviasales.citysearch.router

import ru.aviasales.common.domain.model.City

data class CityResult(
    val strategy: SearchStrategy,
    val city: City
)