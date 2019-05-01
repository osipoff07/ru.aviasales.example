package ru.aviasales.citysearch.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ru.aviasales.R
import ru.aviasales.cityview.model.CityModel
import ru.aviasales.cityview.presentation.DefaultCityView
import ru.aviasales.extension.inflate

class CityAdapter: RecyclerView.Adapter<CityViewHolder<DefaultCityView>>(), CityViewHolder.CityViewItemClickListener {

    private var clickListener: CityItemClickListener = EmptyCityItemClickListener
    private val data: MutableList<CityModel> = mutableListOf()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): CityViewHolder<DefaultCityView> = CityViewHolder(
        cityView = viewGroup.inflate(R.layout.layout_city_list_item) as DefaultCityView,
        listener = this
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(
        cityView: CityViewHolder<DefaultCityView>,
        position: Int
    ) =  cityView.bindView(data[position])

    override fun onCityViewClicked(
        cityModel: CityModel
    ) = clickListener.onCityClicked(cityModel)

    fun setData(items: List<CityModel>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun setItemClickListener(
        listener: CityItemClickListener
    ) {
        clickListener = listener
    }

    interface CityItemClickListener {

        fun onCityClicked(item: CityModel)
    }
}