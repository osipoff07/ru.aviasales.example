package ru.aviasales.common.domain.model

data class City(
    val regionData: RegionData,
    val country: Country,
    val location: Location,
    val timeZone: TimeZone,
    val fullName: String,
    val latinFullName: String,
    val iata: List<String>,
    val hotelsCount: Int
)