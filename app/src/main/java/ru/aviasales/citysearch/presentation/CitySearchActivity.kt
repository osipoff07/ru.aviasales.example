package ru.aviasales.citysearch.presentation

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import ru.aviasales.R
import ru.aviasales.citysearch.domain.EMPTY_USER_INPUT
import ru.aviasales.citysearch.presentation.adapter.CityAdapter
import ru.aviasales.citysearch.router.CityResult
import ru.aviasales.citysearch.router.CitySearchRouter
import ru.aviasales.citysearch.router.SearchStrategy
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.common.domain.NoConnectionException
import ru.aviasales.common.domain.NotFoundException
import ru.aviasales.common.domain.ServerResponseException
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.presentation.Event
import java.lang.Exception

class CitySearchActivity : AppCompatActivity(),
    CityAdapter.CityItemClickListener, TextWatcher {

    private val viewModel: CitySearchViewModel by viewModel()
    private val router: CitySearchRouter by inject()
    private val cityAdapter: CityAdapter = CityAdapter()
    private lateinit var strategy: SearchStrategy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_search)
        strategy = router.unpackStrategy(intent)
        setupRecyclerView()
        initViewModel()
        setupActionBar()
    }

    override fun onSupportNavigateUp(): Boolean = true.also {
        onBackPressed()
    }

    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit

    override fun onTextChanged(
        charSequence: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        val userInput: String = charSequence?.toString() ?: EMPTY_USER_INPUT
        viewModel.onUserInputChanged(userInput)
    }

    override fun onCityClicked(
        item: CityModel
    ) = viewModel.onCitySelected(item)

    private fun setupActionBar() {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.layout_search_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val view: View = supportActionBar?.customView ?: return
        setupActionBarEditText(view)
    }

    private fun setupActionBarEditText(view: View) {
        val editText: EditText = view.findViewById(R.id.layout_search_toolbar_search_view)
        val hintId: Int = when (router.unpackStrategy(intent)) {
            is SearchStrategy.From -> R.string.activity_city_search_from_hint
            is SearchStrategy.To -> R.string.activity_city_search_to_hint
        }
        editText.setHint(hintId)
        editText.addTextChangedListener(this)
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.activity_city_search_recycler_view)
        cityAdapter.setItemClickListener(this)
        recyclerView.adapter = cityAdapter
    }

    private fun initViewModel() {
        lifecycle.addObserver(viewModel)
        viewModel.getAutoCompleteCitiesLiveData().observe(this, object: Observer<List<CityModel>> {
            override fun onChanged(t: List<CityModel>?) {
                val data: List<CityModel> = t ?: return

                cityAdapter.setData(data)
            }
        })
        viewModel.getLoadingExceptionLiveData().observe(this, object: Observer<Exception> {
            override fun onChanged(t: Exception?) {
                val exception: Exception = t ?: return

                onException(exception)
            }
        })
        viewModel.getSelectedCityLiveDataLiveData().observe(this, object: Observer<Event<City>> {
            override fun onChanged(t: Event<City>?) {
                val data: City = t?.getDataIfNotDelivered() ?: return

                onCitySelected(data)
            }

        })
    }

    private fun onException(exception: Exception) {
        @StringRes
        val message: Int = when(exception) {
            is NoConnectionException -> R.string.default_no_connection_exception
            is NotFoundException -> R.string.default_not_found_exception
            is ServerResponseException -> R.string.default_server_response_exception
            else -> R.string.default_exception_text
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onCitySelected(
        city: City
    ) {
        val intent: Intent = router.createResultIntent(
            CityResult(
                strategy = strategy,
                city = city
            )
        )
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
