package com.anum.locdagger.ui.vehiclesMap

import com.anum.locdagger.di.scope.FragmentScope
import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.service.api.LocationService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class VehicleMapModule {

    @FragmentScope
    @Provides fun getVehicleMapPresenter(locationService: LocationService, compositeDisposable: CompositeDisposable,
                                         appSchedulerProvider: AppSchedulerProvider
    ) : VehiclesMapContract.Presenter =
        VehiclesMapPresenterImpl(locationService, compositeDisposable, appSchedulerProvider)


    @FragmentScope
    @Provides fun getCompositeDisposible() : CompositeDisposable = CompositeDisposable()
}