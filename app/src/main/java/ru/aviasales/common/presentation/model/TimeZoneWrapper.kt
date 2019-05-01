package ru.aviasales.common.presentation.model

import android.os.Parcel
import android.os.Parcelable
import ru.aviasales.common.domain.model.TimeZone

class TimeZoneWrapper(
    val timeZone: TimeZone
): Parcelable {

    companion object CREATOR : Parcelable.Creator<TimeZoneWrapper> {
        override fun createFromParcel(parcel: Parcel): TimeZoneWrapper {
            return TimeZoneWrapper(parcel)
        }

        override fun newArray(size: Int): Array<TimeZoneWrapper?> {
            return arrayOfNulls(size)
        }
    }

    constructor(
        parcel: Parcel
    ) : this(
        timeZone = TimeZone(
            timeName = parcel.readString(),
            timeZoneSecond = parcel.readLong()
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(timeZone.timeName)
        parcel.writeLong(timeZone.timeZoneSecond)
    }

    override fun describeContents(): Int = 0
}