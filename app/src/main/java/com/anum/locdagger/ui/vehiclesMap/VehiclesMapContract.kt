package com.anum.locdagger.ui.vehiclesMap

import com.anum.locdagger.base.BasePresenter
import com.anum.locdagger.ui.vehicle.VehicleFragmentContract

interface VehiclesMapContract {

    interface View: VehicleFragmentContract.View

    interface Presenter: BasePresenter<View> {
        fun getVehicles(lat1: Double, long1: Double, lat2: Double, long2: Double)
    }
}