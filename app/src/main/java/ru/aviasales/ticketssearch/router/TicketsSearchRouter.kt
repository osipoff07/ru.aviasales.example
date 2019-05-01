package ru.aviasales.ticketssearch.router

import android.content.Context
import android.content.Intent
import ru.aviasales.common.presentation.model.CityWrapper
import ru.aviasales.ticketssearch.presentation.TicketsSearchActivity

private const val FROM_CITY: String = "from_city"
private const val TO_CITY: String = "to_city"

class TicketsSearchRouter {

    fun createIntent(
        context: Context,
        ticketsSearchModel: TicketsSearchModel
    ): Intent = Intent(context, TicketsSearchActivity::class.java).apply {
        putExtra(FROM_CITY, CityWrapper(ticketsSearchModel.from))
        putExtra(TO_CITY, CityWrapper(ticketsSearchModel.to))
    }

    fun unpackTickedsSearchModel(
        intent: Intent
    ): TicketsSearchModel = TicketsSearchModel(
        from = intent.getParcelableExtra<CityWrapper>(FROM_CITY).city,
        to = intent.getParcelableExtra<CityWrapper>(TO_CITY).city
    )
}