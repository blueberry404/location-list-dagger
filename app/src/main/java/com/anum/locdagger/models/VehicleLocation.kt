package com.anum.locdagger.models

import android.os.Parcel
import android.os.Parcelable
import com.anum.locdagger.base.BaseEntity

data class VehicleLocation constructor(
    val id: Int,
    val fleetType: String,
    val heading: Float,
    val coordinate: Coordinate,
    var address: String?) : BaseEntity(), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readParcelable(Coordinate::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(fleetType)
        parcel.writeFloat(heading)
        parcel.writeParcelable(coordinate, flags)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VehicleLocation> {
        override fun createFromParcel(parcel: Parcel): VehicleLocation {
            return VehicleLocation(parcel)
        }

        override fun newArray(size: Int): Array<VehicleLocation?> {
            return arrayOfNulls(size)
        }
    }

    fun isFleetTaxi() : Boolean = fleetType.toLowerCase() == "taxi"
}

