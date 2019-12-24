package com.anum.locdagger.ui.vehiclesMap


import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anum.locdagger.R
import com.anum.locdagger.base.BaseFragment
import com.anum.locdagger.helpers.showToast
import com.anum.locdagger.models.Coordinate
import com.anum.locdagger.models.VehicleLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Single
import kotlinx.android.synthetic.main.fragment_vehicles_map.*
import java.io.IOException
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class VehiclesMapFragment : BaseFragment<VehiclesMapContract.View, VehiclesMapContract.Presenter>(),
    VehiclesMapContract.View, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private lateinit var mMap: GoogleMap
    private val args : VehiclesMapFragmentArgs by navArgs()
    private lateinit var focusLocation: VehicleLocation
    private var vehicles: MutableList<VehicleLocation> = mutableListOf()
    private var latLngFocused : LatLngBounds? = null
    @Inject lateinit var presenter : VehiclesMapContract.Presenter

    override fun instantiatePresenter(): VehiclesMapContract.Presenter = presenter

    override fun layoutResource(): Int = R.layout.fragment_vehicles_map

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        AndroidSupportInjection.inject(this)
        return super.onCreateView(inflater, container,
            savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        focusLocation = args.toFocus

        val latLon = focusLocation.coordinate.latLon()
        val cameraPosition = CameraPosition.Builder()
            .target(latLon)
            .zoom(10f)
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        mMap.setOnCameraIdleListener(this)
    }

    override fun onCameraIdle() {
        latLngFocused = mMap.projection.visibleRegion.latLngBounds
        latLngFocused?.let { latlngBounds ->
            val latlngNorthEast = latlngBounds.northeast
            val latlngSouthWest = latlngBounds.southwest
            presenter.getVehicles(latlngNorthEast.latitude, latlngNorthEast.longitude,
                latlngSouthWest.latitude, latlngSouthWest.longitude)
        }
    }

    private fun removeAllMarkers() {
        mMap.clear()
        vehicles.clear()
    }

    private fun showMarkersWithInBounds() {
        vehicles.forEach {
            addMarkerToMap(it)
        }
    }

    private fun addMarkerToMap(location: VehicleLocation) {
        mMap.addMarker(
            MarkerOptions().position(location.coordinate.latLon())
                .title(location.address)
                .icon(
                    BitmapDescriptorFactory.defaultMarker(
                        if (isSelectedLocation(location.coordinate)) BitmapDescriptorFactory.HUE_RED else BitmapDescriptorFactory.HUE_ORANGE
                    )
                )
        )
    }

    private fun isSelectedLocation(coordinate: Coordinate) : Boolean = focusLocation.coordinate == coordinate

    override fun showError(message: String) {
        showToast(context, message)
    }

    override fun showLoader() {
        loaderView.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loaderView.visibility = View.GONE
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

    override fun populateData(list: List<VehicleLocation>?) {
        list?.let {
            removeAllMarkers()
            vehicles.addAll(list)
            vehicles.add(focusLocation)
            showMarkersWithInBounds()
        }
    }

    override fun isLocationWithinUserBounds(location: VehicleLocation): Boolean {
        latLngFocused?.let { return it.contains(location.coordinate.latLon()) }
        return false
    }
}
