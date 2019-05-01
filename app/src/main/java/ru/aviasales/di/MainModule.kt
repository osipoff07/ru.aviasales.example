package ru.aviasales.di

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.aviasales.main.MainViewModel

val mainModule: Module = applicationContext {
    viewModel {
        MainViewModel(
            presentationCityDataMapper = get(CITY_DOMAIN_TO_PRESENTATION_MAPPER)
        )
    }
}