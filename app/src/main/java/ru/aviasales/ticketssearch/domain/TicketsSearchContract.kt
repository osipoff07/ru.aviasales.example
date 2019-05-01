package ru.aviasales.ticketssearch.domain

import ru.aviasales.common.domain.BaseInputPort
import ru.aviasales.common.domain.model.Location
import ru.aviasales.ticketssearch.domain.trajectory.Point

interface TicketsSearchContract {

    interface OutputPort {

        fun setTrajectory(points: List<Point>)
    }

    interface InputPost: BaseInputPort<TicketsSearchContract.OutputPort> {

        fun onNeedsToLoadTrajectory(
            from: Location,
            to: Location
        )
    }
}