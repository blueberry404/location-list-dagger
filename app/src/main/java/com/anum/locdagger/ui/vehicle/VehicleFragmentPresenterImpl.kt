package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.listeners.SchedulerProvider
import com.anum.locdagger.models.Fleets
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.service.api.LocationService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toObservable
import java.lang.ref.WeakReference
import javax.inject.Inject

open class VehicleFragmentPresenterImpl @Inject constructor(var locationService : LocationService,
                                                       var compositeDisposable: CompositeDisposable,
                                                            var schedulerProvider: SchedulerProvider
                                                            ) :
    VehicleFragmentContract.Presenter {

    private var view : WeakReference<VehicleFragmentContract.View>? = null

    override fun attachView(view: VehicleFragmentContract.View) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        this.view = null
        compositeDisposable.dispose()
    }

    override fun getVehicles(url: String, lat1: Double, long1: Double, lat2: Double, long2: Double) {
        view?.get()?.showLoader()
        val disposable = locationService.getVehicles(url,
            lat1, long1, lat2, long2)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
            .subscribe({ fleet: Fleets? ->
                fleet?.let {
                    handleData( filterData(it.list) )
                } ?: run{
                    view?.get()?.showError("No data found")
                }

            }, { error ->
                view?.get()?.hideLoader()
                view?.get()?.showError(error.localizedMessage)
            })
        compositeDisposable.add(disposable)
    }

    fun filterData(list: List<VehicleLocation>) : List<VehicleLocation> {
        view?.get()?.let { view ->
            return list.filter { view.isLocationWithinUserBounds(it) }
        }
        return list
    }

    private fun handleData(list: List<VehicleLocation>) {
        val disposable = list.toObservable()
            .flatMap {
                view?.get()?.getAddressFromLocation(it)!!.toObservable()
            }
            .toList()
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
            .subscribe { vehicles ->
                view?.get()?.hideLoader()
                view?.get()?.populateData(vehicles)
            }
        compositeDisposable.add(disposable)
    }
}