package ru.aviasales.di

import org.koin.dsl.module.Module

val modules: List<Module> = listOf(
    applicationModule,
    mainModule,
    citySearchModule,
    ticketsSearchModule
)