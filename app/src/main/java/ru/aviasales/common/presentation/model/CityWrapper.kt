package ru.aviasales.common.presentation.model

import android.os.Parcel
import android.os.Parcelable
import ru.aviasales.common.domain.model.City

class CityWrapper(
    val city: City
): Parcelable {

    companion object CREATOR : Parcelable.Creator<CityWrapper> {
        override fun createFromParcel(parcel: Parcel): CityWrapper {
            return CityWrapper(parcel)
        }

        override fun newArray(size: Int): Array<CityWrapper?> {
            return arrayOfNulls(size)
        }
    }

    constructor(
        parcel: Parcel
    ) : this(
        city = City(
            regionData = parcel.readParcelable<RegionDataWrapper>(
                RegionDataWrapper::class.java.classLoader
            ).regionData,
            country = parcel.readParcelable<CountryWrapper>(
                CountryWrapper::class.java.classLoader
            ).country,
            location = parcel.readParcelable<LocationWrapper>(
                LocationWrapper::class.java.classLoader
            ).location,
            timeZone = parcel.readParcelable<TimeZoneWrapper>(
                TimeZoneWrapper::class.java.classLoader
            ).timeZone,
            fullName = parcel.readString(),
            latinFullName = parcel.readString(),
            iata = ArrayList<String>().apply { parcel.readStringList(this) },
            hotelsCount = parcel.readInt()
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(RegionDataWrapper(city.regionData), flags)
        parcel.writeParcelable(CountryWrapper(city.country), flags)
        parcel.writeParcelable(LocationWrapper(city.location), flags)
        parcel.writeParcelable(TimeZoneWrapper(city.timeZone), flags)
        parcel.writeString(city.fullName)
        parcel.writeString(city.latinFullName)
        parcel.writeStringList(city.iata)
        parcel.writeInt(city.hotelsCount)
    }

    override fun describeContents(): Int = 0
}