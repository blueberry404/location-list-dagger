package com.anum.locdagger.adapters

import android.view.View
import com.anum.locdagger.R
import com.anum.locdagger.base.BaseAdapter
import com.anum.locdagger.listeners.ItemClickListener
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.viewholders.VehicleLocationViewHolder
import javax.inject.Inject

class VehicleLocationAdapter @Inject constructor(list: MutableList<VehicleLocation>, var clickListener: ItemClickListener?) :
    BaseAdapter<VehicleLocationViewHolder, VehicleLocation>(list) {

    override val itemLayoutResource: Int = R.layout.item_vehicle_location

    override fun getViewHolder(itemView: View, viewType: Int): VehicleLocationViewHolder =
        VehicleLocationViewHolder(itemView, clickListener)
}