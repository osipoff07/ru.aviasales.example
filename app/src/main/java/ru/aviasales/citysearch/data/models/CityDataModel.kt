package ru.aviasales.citysearch.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CityDataModel(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("city")
    val city: String,
    @JsonProperty("latinCity")
    val latinCity: String,
    @JsonProperty("location")
    val location: LocationDataModel,
    @JsonProperty("fullname")
    val fullName: String,
    @JsonProperty("latinFullName")
    val latinFullName: String,
    @JsonProperty("iata")
    val iata: List<String>,
    @JsonProperty("countryId")
    val countryId: Long,
    @JsonProperty("countryCode")
    val countryCode: String,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("latinCountry")
    val latinCountry: String,
    @JsonProperty("hotelsCount")
    val hotelsCount: Int,
    @JsonProperty("timezone")
    val timeZone: String,
    @JsonProperty("timezonesec")
    val timeZoneSec: Long
)
