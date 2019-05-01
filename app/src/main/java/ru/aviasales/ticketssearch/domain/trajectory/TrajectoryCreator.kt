package ru.aviasales.ticketssearch.domain.trajectory

interface TrajectoryCreator {

    fun create(
        firstPoint: Point,
        secondPoint: Point,
        pointCount: Int
    ): List<Point>
}