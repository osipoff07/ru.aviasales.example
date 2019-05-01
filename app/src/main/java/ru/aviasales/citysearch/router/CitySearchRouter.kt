package ru.aviasales.citysearch.router

import android.content.Context
import android.content.Intent
import ru.aviasales.citysearch.presentation.CitySearchActivity
import ru.aviasales.common.presentation.model.CityWrapper
import java.lang.IllegalArgumentException

private const val SEARCH_STRATEGY_KEY: String = "search_strategy_key"
private const val SEARCH_CITY_RESULT_KEY: String = "search_city_result"
private const val FROM_SEARCH_STRATEGY: String = "from"
private const val TO_SEARCH_STRATEGY: String = "to"

class CitySearchRouter {

    fun createIntent(
        context: Context,
        strategy: SearchStrategy = SearchStrategy.From
    ): Intent = Intent(context, CitySearchActivity::class.java).apply {
        putExtra(SEARCH_STRATEGY_KEY, mapStrategyToString(strategy))
    }

    fun unpackStrategy(
        intent: Intent
    ): SearchStrategy = mapKeyToStrategy(intent.getStringExtra(SEARCH_STRATEGY_KEY))

    fun createResultIntent(
        cityResult: CityResult
    ): Intent = Intent().apply {
        putExtra(SEARCH_STRATEGY_KEY, mapStrategyToString(cityResult.strategy))
        putExtra(SEARCH_CITY_RESULT_KEY, CityWrapper(cityResult.city))
    }

    fun unpackResultIntent(
        intent: Intent
    ): CityResult = CityResult(
        strategy = unpackStrategy(intent),
        city = intent.getParcelableExtra<CityWrapper>(SEARCH_CITY_RESULT_KEY).city
    )

    private fun mapStrategyToString(
        strategy: SearchStrategy
    ): String = when(strategy) {
        is SearchStrategy.From -> FROM_SEARCH_STRATEGY
        is SearchStrategy.To -> TO_SEARCH_STRATEGY
    }

    private fun mapKeyToStrategy(
        key: String
    ): SearchStrategy = when(key) {
        FROM_SEARCH_STRATEGY -> SearchStrategy.From
        TO_SEARCH_STRATEGY -> SearchStrategy.To
        else -> throw IllegalArgumentException("Cannot find search strategy identifier = $key")
    }
}