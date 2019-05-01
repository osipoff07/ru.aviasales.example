package ru.aviasales.cityview.presentation

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import ru.aviasales.R
import ru.aviasales.cityview.CityView
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.extension.inflateToRoot

private const val PARENT_STATE_KEY = "parent_state"
private const val CITY_DATA_KEY = "city_state"

class DefaultCityView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr), CityView {

    private val cityNameTextView: TextView
    private val cityFullNameTextView: TextView
    private val cityIataTextView: TextView
    private val previewText: TextView

    private var cityData: CityModel? = null

    init {
        val view: View = inflateToRoot(R.layout.layout_default_city_view)
        cityNameTextView = view.findViewById(R.id.layout_default_city_view_name)
        cityFullNameTextView = view.findViewById(R.id.layout_default_city_view_full_name)
        cityIataTextView = view.findViewById(R.id.layout_default_city_view_iata)
        previewText = view.findViewById(R.id.layout_default_city_view_preview)
    }

    override fun bindView(
        data: CityModel
    ) {
        this.cityData = data
        previewText.visibility = View.GONE
        cityNameTextView.text = data.cityName
        cityFullNameTextView.text = data.fullName
        cityIataTextView.text = data.iata
    }

    override fun onSaveInstanceState(): Parcelable? = Bundle().apply {
        putParcelable(PARENT_STATE_KEY, super.onSaveInstanceState())
        cityData?.let {
            putParcelable(CITY_DATA_KEY, it)
        }
    }

    override fun onRestoreInstanceState(
        state: Parcelable?
    ): Unit = when (state) {
        is Bundle -> {
            state.getParcelable<CityModel>(CITY_DATA_KEY)?.let {
                bindView(it)
            }
            super.onRestoreInstanceState(state.getParcelable(PARENT_STATE_KEY))
        }
        else -> super.onRestoreInstanceState(state)
    }
}