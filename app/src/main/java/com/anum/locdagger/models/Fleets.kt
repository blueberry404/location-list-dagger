package com.anum.locdagger.models

import android.os.Parcel
import android.os.Parcelable
import com.anum.locdagger.base.BaseEntity
import com.google.gson.annotations.SerializedName

data class Fleets constructor(
    @SerializedName("list")
    val list: List<VehicleLocation>
) : BaseEntity(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(VehicleLocation))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fleets> {
        override fun createFromParcel(parcel: Parcel): Fleets {
            return Fleets(parcel)
        }

        override fun newArray(size: Int): Array<Fleets?> {
            return arrayOfNulls(size)
        }
    }
}