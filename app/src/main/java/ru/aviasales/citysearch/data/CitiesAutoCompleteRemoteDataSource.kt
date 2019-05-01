package ru.aviasales.citysearch.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aviasales.citysearch.data.models.CityAutoCompleteModel

interface CitiesAutoCompleteRemoteDataSource {

    @GET("/autocomplete")
    fun getCities(
        @Query("term") term: String,
        @Query("lang") lang: String
    ): Call<CityAutoCompleteModel>
}