package ru.aviasales.ticketssearch.domain

import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.Location
import ru.aviasales.ticketssearch.domain.trajectory.Point
import ru.aviasales.ticketssearch.domain.trajectory.TrajectoryCreator

private const val POINTS_COUNT: Int = 500

class TicketsSearchInteractor(
    private val trajectoryCreator: TrajectoryCreator,
    private val mapper: Mapper<Location, Point>,
    private val pointsCount: Int = POINTS_COUNT
): TicketsSearchContract.InputPost {

    private var outputPort: TicketsSearchContract.OutputPort = EmptyOutputPort

    override fun attachOutputPort(
        outputPort: TicketsSearchContract.OutputPort
    ) {
        this.outputPort = outputPort
    }

    override fun detachOutputPort() {
        outputPort = EmptyOutputPort
    }

    override fun onNeedsToLoadTrajectory(
        from: Location,
        to: Location
    ) {
        val points: List<Point> = trajectoryCreator.create(
            firstPoint = mapper.map(from),
            secondPoint = mapper.map(to),
            pointCount = pointsCount
        )
        outputPort.setTrajectory(points)
    }
}