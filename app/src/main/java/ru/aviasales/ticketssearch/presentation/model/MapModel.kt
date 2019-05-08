package ru.aviasales.ticketssearch.presentation.model

import com.google.android.gms.maps.model.LatLng

sealed class MapModel {

    data class IataMarker(
        val point: LatLng,
        val text: String
    )

    data class PlaneMarker(
        val point: LatLng,
        val angle: Float
    )

    data class Path(
        val data: List<LatLng>
    )

    data class CameraPosition(
        val fromPosition: LatLng,
        val toPosition: LatLng
    )
}