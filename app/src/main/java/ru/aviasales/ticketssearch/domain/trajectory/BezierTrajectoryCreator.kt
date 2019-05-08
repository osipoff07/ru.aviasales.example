package ru.aviasales.ticketssearch.domain.trajectory

import java.lang.Math.*

private const val MAXIMUM_DIF: Double = 40.0
private const val MAXIMUM_SIZE: Double = 40.0
private const val DEFAULT_ANGLE: Double = -60.0
private const val DEFAULT_DIFF_ANGLE: Double = 20.0

class BezierTrajectoryCreator: TrajectoryCreator {

    override fun create(
        firstPoint: Point,
        secondPoint: Point,
        pointCount: Int
    ): List<Point> {
        val curvePair: Pair<Point, Point> = getCurvePair(firstPoint, secondPoint)

        return (0..pointCount).map {
            val t = it.toDouble()/pointCount.toDouble()
            val arcX = ((1 - t) * (1 - t) * (1 - t) * firstPoint.x
                    + 3 * (1 - t) * (1 - t) * t * curvePair.first.x
                    + 3 * (1 - t) * t * t * curvePair.second.x
                    + t * t * t * secondPoint.x)
            val arcY = ((1 - t) * (1 - t) * (1 - t) * firstPoint.y
                    + 3 * (1 - t) * (1 - t) * t * curvePair.first.y
                    + 3 * (1 - t) * t * t * curvePair.second.y
                    + t * t * t * secondPoint.y)

            Point(arcX, arcY)
        }
    }

    private fun getCurvePair(
        firstPoint: Point,
        secondPoint: Point
    ): Pair<Point, Point> {
        val diffX = abs(firstPoint.x - secondPoint.x)
        val diffY = abs(firstPoint.y - secondPoint.y)
        val maxDif = max(diffX, diffY)
        if (maxDif > MAXIMUM_DIF) {

            return getCurveWithMaximumDiff(firstPoint, secondPoint)
        }
        val minDif = min(diffX, diffY)

        return getCurveWithAngle(firstPoint, secondPoint, minDif/maxDif)
    }

    private fun getCurveWithAngle(
        firstPoint: Point,
        secondPoint: Point,
        diffCoff: Double
    ): Pair<Point, Point> {
        val middlePoint: Point = getCenter(firstPoint, secondPoint)
        val firstCurve: Point = rotatePoint(middlePoint, firstPoint, diffCoff)
        val secondCurve: Point = rotatePoint(middlePoint, secondPoint, diffCoff)

        return Pair(
            firstCurve,
            secondCurve
        )
    }

    private fun getCenter(
        first: Point,
        second: Point
    ): Point = Point(
        (second.x + first.x)/2,
        (second.y + first.y)/2
    )

    private fun rotatePoint(
        centerPoint: Point,
        point: Point,
        diffCoef: Double
    ): Point {
        val diffX: Double = point.x - centerPoint.x
        val diffY: Double = point.y - centerPoint.y
        val angle: Double = Math.toRadians(DEFAULT_ANGLE + (DEFAULT_DIFF_ANGLE * diffCoef))
        val angleCos: Double = cos(angle)
        val angleSin: Double = sin(angle)
        val x: Double = angleCos * diffX - angleSin * diffY + centerPoint.x
        val y: Double = angleSin * diffX + angleCos * diffY + centerPoint.y

        return Point(x, y)
    }

    fun getCurveWithMaximumDiff(
        firstPoint: Point,
        secondPoint: Point
    ): Pair<Point, Point> {
        val diffX = abs(firstPoint.x - secondPoint.x)
        val diffY = abs(firstPoint.y - secondPoint.y)
        val center = getCenter(firstPoint, secondPoint)
        val centerFirstMiddle = getCenter(firstPoint, center)
        val centerSecondMiddle = getCenter(secondPoint, center)

        return if (diffX > diffY) {
            val size = if (diffX / 4 > MAXIMUM_SIZE) { MAXIMUM_SIZE } else { diffX / 4 }

            Pair(
                Point(centerFirstMiddle.x, centerFirstMiddle.y + size),
                Point(centerSecondMiddle.x, centerSecondMiddle.y - size)
            )
        } else {
            val size = if (diffY / 4 > MAXIMUM_SIZE) { MAXIMUM_SIZE } else { diffY / 4 }

            Pair(
                Point(centerFirstMiddle.x  + size, centerFirstMiddle.y),
                Point(centerSecondMiddle.x - size, centerSecondMiddle.y)
            )
        }
    }
}