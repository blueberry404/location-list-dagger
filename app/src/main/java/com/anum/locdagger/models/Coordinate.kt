package com.anum.locdagger.models

import android.os.Parcel
import android.os.Parcelable
import com.anum.locdagger.base.BaseEntity
import com.google.android.gms.maps.model.LatLng

data class Coordinate constructor(
    val latitude: Double,
    val longitude: Double) : BaseEntity(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coordinate> {
        override fun createFromParcel(parcel: Parcel): Coordinate {
            return Coordinate(parcel)
        }

        override fun newArray(size: Int): Array<Coordinate?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other is Coordinate) {
            return other.latitude == latitude && other.longitude == longitude
        }
        return super.equals(other)
    }

    fun latLon() : LatLng = LatLng(latitude, longitude)
}