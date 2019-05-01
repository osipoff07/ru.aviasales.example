package ru.aviasales.citysearch.data

import ru.aviasales.citysearch.domain.CityLocationRepository
import ru.aviasales.common.Response
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.domain.model.RegionData
import ru.aviasales.common.domain.model.Location
import ru.aviasales.common.domain.model.Country
import ru.aviasales.common.domain.model.TimeZone

class DefaultCityLocationRepository: CityLocationRepository {

    //TODO Task No.
    //HARDCODED: no server part yet
    override fun getCityLocation(
        lang: String
    ): Response<City> = Response.Success(
        data = if (lang == "ru") {
            getRu()
        } else {
            getEng()
        }
    )

    private fun getEng(): City = City(
        regionData = RegionData(12196, "St. Petersburg", "St. Petersburg"),
        fullName = "St. Petersburg, Russia",
        latinFullName = "St. Petersburg, Russia",
        location = Location(59.95, 30.316667),
        iata = listOf("LED", "LED"),
        country = Country(RegionData(186, "Russia", "Russia"), "EN"),
        hotelsCount = 2804,
        timeZone = TimeZone("Europe/Moscow", 14400)
    )

    private fun getRu(): City = City(
        regionData = RegionData(12196, "Санкт-Петербург", "St. Petersburg"),
        fullName = "Санкт-Петербург, Россия",
        latinFullName = "St. Petersburg, Russia",
        location = Location(59.95, 30.316667),
        iata = listOf("LED", "LED"),
        country = Country(RegionData(186, "Россия", "Russia"), "RU"),
        hotelsCount = 2804,
        timeZone = TimeZone("Europe/Moscow", 14400)
    )
}