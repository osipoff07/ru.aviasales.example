package ru.aviasales.ticketssearch.domain

import ru.aviasales.ticketssearch.domain.trajectory.Point

object EmptyOutputPort: TicketsSearchContract.OutputPort {

    override fun setTrajectory(
        points: List<Point>
    ) = Unit
}