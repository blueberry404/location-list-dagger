package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.adapters.VehicleLocationAdapter
import com.anum.locdagger.di.scope.FragmentScope
import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.service.api.LocationService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class VehicleFragmentModule {

    @FragmentScope
    @Provides fun getVehicleListPresenter(locationService: LocationService, compositeDisposable: CompositeDisposable,
                                          appSchedulerProvider: AppSchedulerProvider
    )
            : VehicleFragmentContract.Presenter =
        VehicleFragmentPresenterImpl(locationService, compositeDisposable, appSchedulerProvider)

    @FragmentScope
    @Provides fun getVehicleLocationAdapter(itemClickListener: VehicleFragment) : VehicleLocationAdapter =
        VehicleLocationAdapter(mutableListOf(), itemClickListener)

    @FragmentScope
    @Provides fun getCompositeDisposible() : CompositeDisposable = CompositeDisposable()
}