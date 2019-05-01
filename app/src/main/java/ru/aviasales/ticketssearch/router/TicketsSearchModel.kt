package ru.aviasales.ticketssearch.router

import ru.aviasales.common.domain.model.City

data class TicketsSearchModel(
    val from: City,
    val to: City
)