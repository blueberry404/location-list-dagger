package com.anum.locdagger.ui.vehicle

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anum.locdagger.R
import com.anum.locdagger.adapters.VehicleLocationAdapter
import com.anum.locdagger.base.BaseEntity
import com.anum.locdagger.base.BaseFragment
import com.anum.locdagger.helpers.showToast
import com.anum.locdagger.listeners.ItemClickListener
import com.anum.locdagger.models.VehicleLocation
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_vehicle.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

class VehicleFragment : BaseFragment<VehicleFragmentContract.View,
        VehicleFragmentContract.Presenter>(), VehicleFragmentContract.View, ItemClickListener {

    @Inject lateinit var adapter : VehicleLocationAdapter
    @Inject lateinit var presenter : VehicleFragmentContract.Presenter
    private var list: List<VehicleLocation>? = null
    private var hamburgBounds : LatLngBounds

    init {
        val builder = LatLngBounds.builder()
        builder.include(LatLng(53.694865, 9.757589))
        builder.include(LatLng(53.394655, 10.099891))
        hamburgBounds = builder.build()
    }

    override fun instantiatePresenter(): VehicleFragmentContract.Presenter =  presenter

    override fun layoutResource(): Int = R.layout.fragment_vehicle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initUI() {
        recyclerLocation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerLocation.adapter = adapter
        presenter.getVehicles("https://some-url.com", 53.694865, 9.757589, 53.394655, 10.099891)
    }

    override fun populateData(list: List<VehicleLocation>?) {
        this.list = list
        list?.let { adapter.addItems(it) }
    }

    override fun showError(message: String) {
        showToast(context, message)
    }

    override fun getAddressFromLocation(vehicleLocation: VehicleLocation) : Single<VehicleLocation> {

        return Single.create {

            val geocoder = Geocoder(context, Locale.ENGLISH)
            try {
                val ( latitude, longitude ) = vehicleLocation.coordinate
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses.isNotEmpty()) {
                    val fetchedAddress = addresses[0]
                    val strAddress = StringBuilder(fetchedAddress.getAddressLine(0))
                    vehicleLocation.address = strAddress.toString()
                    it.onSuccess(vehicleLocation)
                }
                else {
                    it.onSuccess(vehicleLocation)
                }
            }
            catch(ex: IOException) {
                it.onSuccess(vehicleLocation)
            }
        }
    }

    override fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    override fun isLocationWithinUserBounds(location: VehicleLocation) : Boolean =
        hamburgBounds.contains(location.coordinate.latLon())

    override fun onItemClicked(entity: BaseEntity) {
        val location = entity as VehicleLocation
        val direction = VehicleFragmentDirections.actionNavigationVehicleToVehiclesMapFragment(location)
        findNavController().navigate(direction)
    }
}