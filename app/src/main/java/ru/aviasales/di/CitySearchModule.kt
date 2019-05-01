package ru.aviasales.di

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import ru.aviasales.citysearch.data.CitiesAutoCompleteRemoteDataSource
import ru.aviasales.citysearch.data.DefaultCitiesAutoCompleteRepository
import ru.aviasales.citysearch.data.DefaultCityLocationRepository
import ru.aviasales.citysearch.data.DefaultPopularCitiesRepository
import ru.aviasales.citysearch.data.models.CityDataModel
import ru.aviasales.citysearch.domain.*
import ru.aviasales.citysearch.presentation.CitySearchMapper
import ru.aviasales.citysearch.presentation.CitySearchViewModel
import ru.aviasales.citysearch.router.CitySearchRouter
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.data.AutoCompleteCityMapper
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.City

val citySearchModule: Module = applicationContext {
    factory {
        DefaultPopularCitiesRepository() as PopularCitiesRepository
    }
    factory {
        DefaultCityLocationRepository() as CityLocationRepository
    }
    bean {
        get<Retrofit>().create(CitiesAutoCompleteRemoteDataSource::class.java)
    }
    factory {
        DefaultCitiesAutoCompleteRepository(
            dataSource = get(),
            connectionChecker = get(),
            dataMapper = get(CITY_DATA_TO_DOMAIN_MAPPER)
        ) as CitiesAutoCompleteRepository
    }
    factory {
        CitySearchInteractor(
            citiesAutoCompleteRepository = get(),
            popularCitiesRepository = get(),
            locationRepository = get(),
            languageRepository = get()
        ) as CitySearchContract.InputPost
    }
    factory {
        CitySearchRouter()
    }
    viewModel {
        CitySearchViewModel(
            inputPort = get(),
            cityMapper = get(CITY_DOMAIN_TO_PRESENTATION_MAPPER)
        )
    }
}