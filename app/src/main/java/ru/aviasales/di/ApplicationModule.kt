package ru.aviasales.di

import com.fasterxml.jackson.databind.ObjectMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.aviasales.citysearch.data.models.CityDataModel
import ru.aviasales.citysearch.presentation.CitySearchMapper
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.data.AutoCompleteCityMapper
import ru.aviasales.common.data.SystemConnectionChecker
import ru.aviasales.common.data.SystemUserLanguageRepository
import ru.aviasales.common.domain.ConnectionChecker
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.UserLanguageRepository
import ru.aviasales.common.domain.model.City

private const val API_ENDPOINT: String = "https://yasen.hotellook.com"

const val CITY_DATA_TO_DOMAIN_MAPPER = "city_data_to_domain_mapper"
const val CITY_DOMAIN_TO_PRESENTATION_MAPPER = "city_domain_to_presentation_mapper"

val applicationModule: Module = applicationContext {
    bean {
        ObjectMapper()
    }
    bean {
        Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
    bean {
        SystemConnectionChecker(
            context = androidApplication()
        ) as ConnectionChecker
    }
    bean {
        SystemUserLanguageRepository() as UserLanguageRepository
    }
    factory(CITY_DATA_TO_DOMAIN_MAPPER) {
        AutoCompleteCityMapper() as Mapper<CityDataModel, City>
    }
    factory(CITY_DOMAIN_TO_PRESENTATION_MAPPER) {
        CitySearchMapper() as Mapper<City, CityModel>
    }
}