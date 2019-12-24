package com.anum.locdagger.viewholders

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.anum.locdagger.R
import com.anum.locdagger.base.BaseEntity
import com.anum.locdagger.base.BaseViewHolder
import com.anum.locdagger.listeners.ItemClickListener
import com.anum.locdagger.models.VehicleLocation


class VehicleLocationViewHolder(itemView: View, var clickListener: ItemClickListener?) : BaseViewHolder(itemView) {

    lateinit var viewLine: View
    lateinit var txtLocation: TextView
    lateinit var vehicleLocation : VehicleLocation

    override fun initUI() {
        viewLine = itemView.findViewById(R.id.viewLine)
        txtLocation = itemView.findViewById(R.id.txtLocation)
    }

    override fun onBind(entity: BaseEntity) {
        vehicleLocation = entity as VehicleLocation
        val color = if (vehicleLocation.isFleetTaxi()) R.color.color_taxi else R.color.color_pooling
        val context = itemView.context
        viewLine.setBackgroundColor(ContextCompat.getColor(context, color))
        txtLocation.text = if (vehicleLocation.address.isNullOrEmpty()) context.getString(R.string.title_empty_address)
        else vehicleLocation.address
    }

    override fun onClick() {
        clickListener?.onItemClicked(vehicleLocation)
    }
}