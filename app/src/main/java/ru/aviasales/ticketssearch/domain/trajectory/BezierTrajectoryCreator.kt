package ru.aviasales.ticketssearch.domain.trajectory

class BezierTrajectoryCreator: TrajectoryCreator {

    override fun create(
        firstPoint: Point,
        secondPoint: Point,
        pointCount: Int
    ): List<Point> {
        val slope: Double = (secondPoint.y - firstPoint.y)/(secondPoint.x - firstPoint.x)
        val rotationAngle: Double = Math.atan(slope)
        val rotationCos: Double = Math.cos(rotationAngle)
        val rotationSin: Double = Math.sin(rotationAngle)
        val amplitude: Double = -slope/(2*Math.PI)
        val k: Double = (secondPoint.x - firstPoint.x)/rotationCos

        return (0..pointCount).map {
            val t: Double = it.toDouble()/pointCount.toDouble()
            val x: Double = k * (rotationCos * t - rotationSin * Math.sin(2 * Math.PI * t)) + firstPoint.x
            val y: Double = k * (rotationSin * t + amplitude * Math.sin(2 * Math.PI * t)) + firstPoint.y
            Point(x, y)
        }
    }
}