package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.base.BasePresenter
import com.anum.locdagger.base.BaseView
import com.anum.locdagger.models.VehicleLocation
import io.reactivex.Single

interface VehicleFragmentContract {

    interface View: BaseView {
        fun populateData(list: List<VehicleLocation>?)
        fun showError(message: String)
        fun getAddressFromLocation(vehicleLocation: VehicleLocation): Single<VehicleLocation>
        fun showLoader()
        fun hideLoader()
        fun isLocationWithinUserBounds(location: VehicleLocation) : Boolean
    }

    interface Presenter: BasePresenter<View> {
        fun getVehicles(url: String, lat1: Double, long1: Double, lat2: Double, long2: Double)
    }
}