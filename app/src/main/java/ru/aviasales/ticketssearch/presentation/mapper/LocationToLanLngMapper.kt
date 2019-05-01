package ru.aviasales.ticketssearch.presentation.mapper

import com.google.android.gms.maps.model.LatLng
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.Location

class LocationToLanLngMapper: Mapper<Location, LatLng> {

    override fun map(
        from: Location
    ): LatLng = LatLng(
        from.latitude,
        from.longitude
    )
}