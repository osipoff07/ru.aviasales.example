package ru.aviasales.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import ru.aviasales.R
import ru.aviasales.citysearch.router.CitySearchRouter
import ru.aviasales.citysearch.router.SearchStrategy
import ru.aviasales.cityview.CityView
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.cityview.presentation.DefaultCityView
import ru.aviasales.common.presentation.Event
import ru.aviasales.ticketssearch.router.TicketsSearchModel
import ru.aviasales.ticketssearch.router.TicketsSearchRouter

private const val SELECT_CITY_RC = 1

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private val citySearchRouter: CitySearchRouter by inject()
    private val ticketsSearchRouter: TicketsSearchRouter by inject()
    private lateinit var fromCityView: CityView
    private lateinit var toCityView: CityView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCitiesView()
        observeViewModel()
        findViewById<Button>(R.id.activity_main_search_button).setOnClickListener {
            viewModel.onCitySearchClicked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != SELECT_CITY_RC || resultCode != Activity.RESULT_OK || data == null) {

            return
        }
        viewModel.onCitySelected(citySearchRouter.unpackResultIntent(data))
    }

    private fun setupCitiesView() {
        fromCityView = findViewById<DefaultCityView>(R.id.activity_main_from_city_view).apply {
            setOnClickListener {
                openCitySearch(SearchStrategy.From)
            }
        }
        toCityView = findViewById<DefaultCityView>(R.id.activity_main_to_city_view).apply {
            setOnClickListener {
                openCitySearch(SearchStrategy.To)
            }
        }
    }

    private fun openCitySearch(
        strategy: SearchStrategy
    ) {
        val intent: Intent = citySearchRouter.createIntent(this, strategy)
        startActivityForResult(intent, SELECT_CITY_RC)
    }

    private fun observeViewModel() {
        viewModel.getFromCityLiveData().observe(this, object: Observer<CityModel> {

            override fun onChanged(t: CityModel?) {
                val data: CityModel = t ?: return

                fromCityView.bindView(data)
            }
        })
        viewModel.getToCityLiveData().observe(this, object: Observer<CityModel> {

            override fun onChanged(t: CityModel?) {
                val data: CityModel = t ?: return

                toCityView.bindView(data)
            }
        })
        viewModel.getNavigationLiveData().observe(this, object: Observer<Event<TicketsSearchModel>> {

            override fun onChanged(t: Event<TicketsSearchModel>?) {
                val data: TicketsSearchModel = t?.getDataIfNotDelivered() ?: return

                openTicketSearch(data)
            }
        })
    }

    private fun openTicketSearch(
        data: TicketsSearchModel
    ) {
        val intent: Intent = ticketsSearchRouter.createIntent(this, data)
        startActivity(intent)
    }
}
