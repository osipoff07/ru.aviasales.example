package ru.aviasales.common.presentation.model

import android.os.Parcel
import android.os.Parcelable
import ru.aviasales.common.domain.model.RegionData

class RegionDataWrapper(
    val regionData: RegionData
): Parcelable {
    companion object CREATOR : Parcelable.Creator<RegionDataWrapper> {
        override fun createFromParcel(parcel: Parcel): RegionDataWrapper {
            return RegionDataWrapper(parcel)
        }

        override fun newArray(size: Int): Array<RegionDataWrapper?> {
            return arrayOfNulls(size)
        }
    }

    constructor(
        parcel: Parcel
    ) : this(
        regionData = RegionData(
            id = parcel.readLong(),
            name = parcel.readString(),
            latinName = parcel.readString()
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(regionData.id)
        parcel.writeString(regionData.name)
        parcel.writeString(regionData.latinName)
    }

    override fun describeContents(): Int = 0
}