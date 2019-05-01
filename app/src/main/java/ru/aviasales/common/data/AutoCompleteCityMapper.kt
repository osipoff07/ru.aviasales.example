package ru.aviasales.common.data

import ru.aviasales.citysearch.data.models.CityDataModel
import ru.aviasales.citysearch.data.models.LocationDataModel
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.*

class AutoCompleteCityMapper: Mapper<CityDataModel, City> {

    override fun map(
        from: CityDataModel
    ): City = City(
        regionData = getCityRegion(from),
        fullName = from.fullName,
        latinFullName = from.latinFullName,
        location = getLocation(from.location),
        iata = from.iata,
        country = getCountry(from),
        hotelsCount = from.hotelsCount,
        timeZone = getTimeZone(from)
    )

    private fun getCityRegion(
        from: CityDataModel
    ): RegionData = RegionData(
        id = from.id,
        name = from.city,
        latinName = from.latinCity
    )

    private fun getCountry(
        from: CityDataModel
    ): Country = Country(
        regionData = getCountryRegion(from),
        countryCode = from.countryCode
    )

    private fun getLocation(
        from: LocationDataModel
    ): Location = Location(
        latitude = from.latitude,
        longitude = from.longitude
    )

    private fun getCountryRegion(
        from: CityDataModel
    ): RegionData = RegionData(
        id = from.countryId,
        name = from.country,
        latinName = from.latinCountry
    )

    private fun getTimeZone(
        from: CityDataModel
    ): TimeZone = TimeZone(
        timeName = from.timeZone,
        timeZoneSecond = from.timeZoneSec
    )
}