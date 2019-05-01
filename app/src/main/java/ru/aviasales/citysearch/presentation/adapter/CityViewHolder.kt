package ru.aviasales.citysearch.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import ru.aviasales.cityview.CityView
import ru.aviasales.cityview.model.CityModel

class CityViewHolder<T>(
    private val cityView: T,
    private val listener: CityViewItemClickListener
): RecyclerView.ViewHolder(cityView), CityView where T: View, T: CityView {

    private var cityModel: CityModel? = null

    init {
        itemView.setOnClickListener {
            cityModel?.let { listener.onCityViewClicked(it) }
        }
    }

    override fun bindView(
        cityData: CityModel
    ) {
        cityModel = cityData
        cityView.bindView(cityData)
    }

    interface CityViewItemClickListener {

        fun onCityViewClicked(cityModel: CityModel)
    }
}