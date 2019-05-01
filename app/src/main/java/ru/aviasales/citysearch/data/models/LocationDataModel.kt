package ru.aviasales.citysearch.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LocationDataModel(
    @JsonProperty("lat")
    val latitude: Double,
    @JsonProperty("lon")
    val longitude: Double
)