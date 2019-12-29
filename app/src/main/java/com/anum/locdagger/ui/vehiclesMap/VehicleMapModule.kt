package com.anum.locdagger.ui.vehiclesMap

import com.anum.locdagger.di.scope.FragmentScope
import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.repositories.VehiclesRepository
import com.anum.locdagger.repositories.VehiclesRepositoryImpl
import com.anum.locdagger.service.api.LocationService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class VehicleMapModule {

    @FragmentScope
    @Provides fun getVehicleMapPresenter(repository: VehiclesRepository, compositeDisposable: CompositeDisposable,
                                         appSchedulerProvider: AppSchedulerProvider
    ) : VehiclesMapContract.Presenter =
        VehiclesMapPresenterImpl(repository, compositeDisposable, appSchedulerProvider)


    @FragmentScope
    @Provides fun getCompositeDisposible() : CompositeDisposable = CompositeDisposable()

    @FragmentScope
    @Provides fun getVehiclesRepository(locationService: LocationService) : VehiclesRepository =
        VehiclesRepositoryImpl(locationService)
}