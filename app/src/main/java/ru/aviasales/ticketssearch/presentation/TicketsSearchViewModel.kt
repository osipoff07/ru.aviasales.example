package ru.aviasales.ticketssearch.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.mapList
import ru.aviasales.common.domain.model.City
import ru.aviasales.common.domain.model.Location
import ru.aviasales.common.presentation.CoroutineScopeViewModel
import ru.aviasales.ticketssearch.domain.TicketsSearchContract
import ru.aviasales.ticketssearch.domain.trajectory.Point
import ru.aviasales.ticketssearch.presentation.model.MapModel
import java.util.*

private const val ROUND_ANGLE: Float = 360f
private const val DEFAULT_DELAY: Long = 0L
private const val DEFAULT_TIMER_PERIOD: Long = 20L
private const val DEFAULT_PLANE_ANGLE: Float = 90.0f
private const val DEFAULT_IATA: String = "NON"

class TicketsSearchViewModel(
    private val fromCity: City,
    private val toCity: City,
    private val inputPort: TicketsSearchContract.InputPost,
    private val pointToLatLngMapper: Mapper<Point, LatLng>,
    private val locationToLatLngMapper: Mapper<Location, LatLng>
): CoroutineScopeViewModel(), TicketsSearchContract.OutputPort {

    private val trajectory: MutableList<Point> = mutableListOf()

    private val fromIataMarkerLiveData: MutableLiveData<MapModel.IataMarker> = MutableLiveData()
    private val toIataMarkerLiveData: MutableLiveData<MapModel.IataMarker> = MutableLiveData()
    private val planeMarkerLiveData: MutableLiveData<MapModel.PlaneMarker> = MutableLiveData()
    private val pathLiveData: MutableLiveData<MapModel.Path> = MutableLiveData()
    private val cameraPositionLiveData: MutableLiveData<MapModel.CameraPosition> = MutableLiveData()

    init {
        inputPort.attachOutputPort(this)
        inputPort.onNeedsToLoadTrajectory(
            from = fromCity.location,
            to = toCity.location
        )
        fromIataMarkerLiveData.value = MapModel.IataMarker(
            point = locationToLatLngMapper.map(fromCity.location),
            text = fromCity.iata.firstOrNull() ?: DEFAULT_IATA
        )
        toIataMarkerLiveData.value = MapModel.IataMarker(
            point = locationToLatLngMapper.map(toCity.location),
            text = toCity.iata.firstOrNull() ?: DEFAULT_IATA
        )
        cameraPositionLiveData.value = MapModel.CameraPosition(
            fromPosition = locationToLatLngMapper.map(fromCity.location),
            toPosition = locationToLatLngMapper.map(toCity.location)
        )
    }

    override fun onCleared() {
        super.onCleared()
        inputPort.detachOutputPort()
    }

    override fun setTrajectory(
        points: List<Point>
    ) {
        trajectory.addAll(points)
        pathLiveData.value = MapModel.Path(
            data = pointToLatLngMapper.mapList(points)
        )
        startPlaneAnimation()
    }

    fun getFromCityMarkerLiveData(): LiveData<MapModel.IataMarker> = fromIataMarkerLiveData

    fun getToCityMarkerLiveData(): LiveData<MapModel.IataMarker> = toIataMarkerLiveData

    fun getPlaneMarkerLiveData(): LiveData<MapModel.PlaneMarker> = planeMarkerLiveData

    fun getPathLiveData(): LiveData<MapModel.Path> = pathLiveData

    fun getCameraPositionLiveData(): LiveData<MapModel.CameraPosition> = cameraPositionLiveData

    private fun startPlaneAnimation() {
        var index = 0
        val timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    if (index >= trajectory.size - 1) {
                        timer.cancel()

                        return
                    }
                    changePlanePosition(index++)
                }
            },
            DEFAULT_DELAY,
            DEFAULT_TIMER_PERIOD
        )
    }

    private fun changePlanePosition(
        index: Int
    ) {
        val lastPosition: Point = getPlaneLastPosition(index)
        val angle: Float = getAngle(lastPosition, trajectory[index]) + DEFAULT_PLANE_ANGLE
        planeMarkerLiveData.postValue(
            MapModel.PlaneMarker(
                point = pointToLatLngMapper.map(trajectory[index]),
                angle = angle
            )
        )
    }

    private fun getPlaneLastPosition(
        nextPosition: Int
    ): Point = if (nextPosition <= 0) {
        trajectory.first()
    } else {
        trajectory[nextPosition-1]
    }

    private fun getAngle(
        first: Point,
        second: Point
    ): Float {
        var angle: Double = Math.toDegrees(Math.atan2(first.y - second.y, first.x - second.x))
        if (angle < 0) {
            angle += ROUND_ANGLE
        }

        return angle.toFloat()
    }
}