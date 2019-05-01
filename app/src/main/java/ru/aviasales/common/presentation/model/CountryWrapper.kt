package ru.aviasales.common.presentation.model

import android.os.Parcel
import android.os.Parcelable
import ru.aviasales.common.domain.model.Country

class CountryWrapper(
    val country: Country
): Parcelable {

    companion object CREATOR : Parcelable.Creator<CountryWrapper> {
        override fun createFromParcel(parcel: Parcel): CountryWrapper {
            return CountryWrapper(parcel)
        }

        override fun newArray(size: Int): Array<CountryWrapper?> {
            return arrayOfNulls(size)
        }
    }

    constructor(
        parcel: Parcel
    ): this(
        country = Country(
            regionData = parcel.readParcelable<RegionDataWrapper>(
                RegionDataWrapper::class.java.classLoader
            ).regionData,
            countryCode = parcel.readString()
        )
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(RegionDataWrapper(country.regionData), flags)
        parcel.writeString(country.countryCode)
    }

    override fun describeContents(): Int = 0
}