package ru.aviasales.ticketssearch.presentation.mapper

import com.google.android.gms.maps.model.LatLng
import ru.aviasales.common.domain.Mapper
import ru.aviasales.ticketssearch.domain.trajectory.Point

class PointsToLatLonMapper: Mapper<Point, LatLng> {

    override fun map(
        from: Point
    ): LatLng = LatLng(from.x, from.y)
}