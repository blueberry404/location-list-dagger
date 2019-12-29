package com.anum.locdagger.ui.vehiclesMap

import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.models.Fleets
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.repositories.VehiclesRepository
import com.anum.locdagger.service.api.LocationService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toObservable
import java.lang.ref.WeakReference
import javax.inject.Inject

class VehiclesMapPresenterImpl @Inject constructor(var repository: VehiclesRepository,
                                                   var compositeDisposable: CompositeDisposable,
                                                   var schedulerProvider: AppSchedulerProvider)
    : VehiclesMapContract.Presenter {

    private var view : WeakReference<VehiclesMapContract.View>? = null

    override fun attachView(view: VehiclesMapContract.View) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        this.view = null
        compositeDisposable.dispose()
    }

    override fun getVehicles(lat1: Double, long1: Double, lat2: Double, long2: Double) {
        view?.get()?.showLoader()
        val disposable = repository.getVehiclesList("https://fake-poi-api.mytaxi.com",
            lat1, long1, lat2, long2)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.uiScheduler())
            .subscribe({ fleet: Fleets? ->
                fleet?.let {
                    it.list?.let { list ->
                        handleData( list )
                    } ?: run {
                        view?.get()?.hideLoader()
                        view?.get()?.showError("No data found")
                    }

                } ?: run {
                    view?.get()?.hideLoader()
                    view?.get()?.showError("No data found")
                }

            }, { error ->
                view?.get()?.hideLoader()
                view?.get()?.showError(error.localizedMessage)
            })
        compositeDisposable.add(disposable)
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