package ru.aviasales.ticketssearch.presentation.mapper

import com.google.android.gms.maps.model.LatLng
import ru.aviasales.common.domain.Mapper
import ru.aviasales.ticketssearch.domain.trajectory.Point

class LatLngToPointsMapper: Mapper<LatLng, Point> {

    override fun map(
        from: LatLng
    ): Point = Point(
        from.latitude,
        from.longitude
    )
}