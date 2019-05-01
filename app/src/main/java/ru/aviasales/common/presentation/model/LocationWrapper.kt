package ru.aviasales.common.presentation.model

import android.os.Parcel
import android.os.Parcelable
import ru.aviasales.common.domain.model.Location

class LocationWrapper(
    val location: Location
): Parcelable {

    companion object CREATOR : Parcelable.Creator<LocationWrapper> {
        override fun createFromParcel(parcel: Parcel): LocationWrapper {
            return LocationWrapper(parcel)
        }

        override fun newArray(size: Int): Array<LocationWrapper?> {
            return arrayOfNulls(size)
        }
    }

    constructor(
        parcel: Parcel
    ) : this(
        location = Location(
            latitude = parcel.readDouble(),
            longitude = parcel.readDouble()
        )
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int
    ) {
        parcel.writeDouble(location.latitude)
        parcel.writeDouble(location.longitude)
    }

    override fun describeContents(): Int = 0
}