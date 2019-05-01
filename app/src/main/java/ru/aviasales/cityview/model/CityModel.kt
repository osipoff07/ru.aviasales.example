package ru.aviasales.cityview.model

import android.os.Parcel
import android.os.Parcelable

data class CityModel(
    val id: Long,
    val cityName: String,
    val fullName: String,
    val iata: String
): Parcelable {
    companion object CREATOR : Parcelable.Creator<CityModel> {
        override fun createFromParcel(parcel: Parcel): CityModel =
            CityModel(parcel)

        override fun newArray(size: Int): Array<CityModel?> = arrayOfNulls(size)
    }

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(cityName)
        parcel.writeString(fullName)
        parcel.writeString(iata)
    }

    override fun describeContents(): Int = 0
}