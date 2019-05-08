package ru.aviasales.ticketssearch.presentation

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import org.koin.android.ext.android.get
import ru.aviasales.R
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.widget.TextView
import android.graphics.Canvas
import android.support.annotation.ColorInt
import android.view.View
import android.view.ViewGroup
import android.view.View.MeasureSpec
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps. model.Dot
import com.google.android.gms.maps.model.PatternItem
import org.koin.android.architecture.ext.viewModel
import ru.aviasales.di.FROM_CITY_PARAM_KEY
import ru.aviasales.di.TO_CITY_PARAM_KEY
import ru.aviasales.ticketssearch.router.TicketsSearchModel
import ru.aviasales.ticketssearch.router.TicketsSearchRouter
import ru.aviasales.ticketssearch.presentation.model.MapModel
import com.google.android.gms.maps.CameraUpdateFactory

private const val PATTERN_GAP_LENGTH_PX: Int = 8
private const val DEFAULT_PATH_WIDTH: Float = 10.0f
private const val DEFAULT_ANCHOR: Float = 0.5f
private const val TOP_PLANE_ANCHOR: Float = 0.5f
private const val DEFAULT_MAP_PADDING: Int = 300
@ColorInt
private const val PATH_POINT_COLOR: Int = 0x70000000
private const val PATH_Z_INDEX: Float = 10F
private const val IATA_Z_INDEX: Float = 11F
private const val PLANE_Z_INDEX: Float = 12F

class TicketsSearchActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: TicketsSearchViewModel by viewModel(
        parameters = {
            mapOf(
                FROM_CITY_PARAM_KEY to model.from,
                TO_CITY_PARAM_KEY to model.to
            )
        }
    )

    private val ticketsSearchRouter: TicketsSearchRouter = get()
    private var planeMarker: Marker? = null
    private lateinit var map: GoogleMap
    private lateinit var model: TicketsSearchModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tickets_search)
        model = ticketsSearchRouter.unpackTickedsSearchModel(intent)
        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getFromCityMarkerLiveData().observe(this, object: Observer<MapModel.IataMarker>{
            override fun onChanged(t: MapModel.IataMarker?) {
                val data: MapModel.IataMarker = t ?: return

                createMarker(data)
            }
        })
        viewModel.getToCityMarkerLiveData().observe(this, object: Observer<MapModel.IataMarker>{
            override fun onChanged(t: MapModel.IataMarker?) {
                val data: MapModel.IataMarker = t ?: return

                createMarker(data)
            }
        })
        viewModel.getPathLiveData().observe(this, object: Observer<MapModel.Path>{
            override fun onChanged(t: MapModel.Path?) {
                val data: MapModel.Path = t ?: return

                showPath(data)
            }
        })
        viewModel.getPlaneMarkerLiveData().observe(this, object: Observer<MapModel.PlaneMarker>{
            override fun onChanged(t: MapModel.PlaneMarker?) {
                val data: MapModel.PlaneMarker = t ?: return

                changePlanePosition(data)
            }
        })
        viewModel.getCameraPositionLiveData().observe(this, object: Observer<MapModel.CameraPosition>{
            override fun onChanged(t: MapModel.CameraPosition?) {
                val data: MapModel.CameraPosition = t ?: return

                changeCameraPosition(data)
            }
        })
    }

    private fun createMarker(
        data: MapModel.IataMarker
    ) {
        val icon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(createCustomMarker(data.text))
        map.addMarker(
            MarkerOptions()
                .position(data.point)
                .icon(icon)
                .anchor(DEFAULT_ANCHOR, DEFAULT_ANCHOR)
                .zIndex(IATA_Z_INDEX)
        )
    }

    private fun createCustomMarker(
        text: String
    ): Bitmap {
        val markerView: View = layoutInflater.inflate(R.layout.layout_custom_marker_iata_title, null)
        val textView: TextView = markerView.findViewById(R.id.layout_custom_marker_iata_text_view)
        textView.text = text

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val specWidth: Int = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        markerView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        markerView.measure(specWidth, specWidth)
        markerView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        markerView.buildDrawingCache()
        val bitmap: Bitmap = Bitmap.createBitmap(markerView.measuredWidth, markerView.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        markerView.draw(canvas)

        return bitmap
    }

    private fun showPath(
        path: MapModel.Path
    ) {
        val rectOptions = PolylineOptions()
            .width(DEFAULT_PATH_WIDTH)
            .pattern(createPatternsItem())
            .addAll(path.data)
            .zIndex(PATH_Z_INDEX)
            .color(PATH_POINT_COLOR)
        map.addPolyline(rectOptions)
    }

    private fun createPatternsItem(): List<PatternItem> = listOf(
        Dot(), Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    )

    private fun changePlanePosition(
        data: MapModel.PlaneMarker
    ): Unit = planeMarker?.let {
        changePlainPosition(it, data)
    } ?: createPlainMarker(data)

    private fun changePlainPosition(
        marker: Marker,
        data: MapModel.PlaneMarker
    ) {
        marker.rotation = data.angle
        marker.position = data.point
    }

    private fun createPlainMarker(
        data: MapModel.PlaneMarker
    ) {
        planeMarker = map.addMarker(
            MarkerOptions()
                .position(data.point)
                .rotation(data.angle)
                .icon(BitmapDescriptorFactory.fromResource(ru.aviasales.R.mipmap.ic_plane))
                .anchor(TOP_PLANE_ANCHOR, DEFAULT_ANCHOR)
                .zIndex(PLANE_Z_INDEX)
        )
    }

    private fun changeCameraPosition(
        data: MapModel.CameraPosition
    ) {
        val builder: LatLngBounds.Builder = LatLngBounds.Builder().apply {
            include(data.fromPosition)
            include(data.toPosition)
        }
        map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(builder.build(), DEFAULT_MAP_PADDING)
        )
    }
}
