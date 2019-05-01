package ru.aviasales.citysearch.presentation.adapter

import ru.aviasales.citysearch.presentation.adapter.CityAdapter
import ru.aviasales.cityview.model.CityModel

object EmptyCityItemClickListener: CityAdapter.CityItemClickListener {

    override fun onCityClicked(item: CityModel) = Unit
}