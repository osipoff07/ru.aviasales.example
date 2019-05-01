package ru.aviasales.ticketssearch.domain.mapper

import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.Location
import ru.aviasales.ticketssearch.domain.trajectory.Point

class LocationToPointMapper: Mapper<Location, Point> {

    override fun map(
        from: Location
    ): Point = Point(
        from.latitude,
        from.longitude
    )
}