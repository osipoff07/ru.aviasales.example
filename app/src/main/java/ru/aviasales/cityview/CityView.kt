package ru.aviasales.cityview

import ru.aviasales.cityview.model.CityModel

interface CityView {

    fun bindView(cityData: CityModel)
}