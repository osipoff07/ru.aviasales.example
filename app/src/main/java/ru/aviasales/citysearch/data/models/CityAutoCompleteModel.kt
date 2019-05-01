package ru.aviasales.citysearch.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CityAutoCompleteModel(
    @JsonProperty("cities")
    val cities: List<CityDataModel>
)