package ru.aviasales.di

import com.google.android.gms.maps.model.LatLng
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.aviasales.common.domain.Mapper
import ru.aviasales.common.domain.model.Location
import ru.aviasales.ticketssearch.domain.mapper.LocationToPointMapper
import ru.aviasales.ticketssearch.domain.TicketsSearchContract
import ru.aviasales.ticketssearch.domain.TicketsSearchInteractor
import ru.aviasales.ticketssearch.domain.trajectory.BezierTrajectoryCreator
import ru.aviasales.ticketssearch.domain.trajectory.Point
import ru.aviasales.ticketssearch.domain.trajectory.TrajectoryCreator
import ru.aviasales.ticketssearch.presentation.mapper.LocationToLanLngMapper
import ru.aviasales.ticketssearch.presentation.mapper.PointsToLatLonMapper
import ru.aviasales.ticketssearch.presentation.TicketsSearchViewModel
import ru.aviasales.ticketssearch.router.TicketsSearchRouter

const val FROM_CITY_PARAM_KEY: String = "from_city"
const val TO_CITY_PARAM_KEY: String = "to_city"

private const val LOCATION_TO_POINT_MAPPER_KEY: String = "location_to_point_mapper"
private const val POINT_TO_LAT_LNG_MAPPER_KEY: String = "point_to_lan_lng_mapper"
private const val LOCATION_TO_LAN_LNG_MAPPER: String = "location_to_lan_lng_mapper"

val ticketsSearchModule: Module = applicationContext {
    bean {
        TicketsSearchRouter()
    }
    bean(LOCATION_TO_POINT_MAPPER_KEY) {
        LocationToPointMapper() as Mapper<Location, Point>
    }
    bean(POINT_TO_LAT_LNG_MAPPER_KEY) {
        PointsToLatLonMapper() as Mapper<Point, LatLng>
    }
    bean(LOCATION_TO_LAN_LNG_MAPPER) {
        LocationToLanLngMapper() as Mapper<Location, LatLng>
    }
    bean {
        BezierTrajectoryCreator() as TrajectoryCreator
    }
    factory {
        TicketsSearchInteractor(
            trajectoryCreator = get(),
            mapper = get(LOCATION_TO_POINT_MAPPER_KEY)
        ) as TicketsSearchContract.InputPost
    }
    viewModel {
        TicketsSearchViewModel(
            fromCity = it[FROM_CITY_PARAM_KEY],
            toCity = it[TO_CITY_PARAM_KEY],
            inputPort = get(),
            pointToLatLngMapper = get(POINT_TO_LAT_LNG_MAPPER_KEY),
            locationToLatLngMapper = get(LOCATION_TO_LAN_LNG_MAPPER)
        )
    }
}