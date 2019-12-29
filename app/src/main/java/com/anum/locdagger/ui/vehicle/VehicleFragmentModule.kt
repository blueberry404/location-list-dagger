package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.adapters.VehicleLocationAdapter
import com.anum.locdagger.di.scope.FragmentScope
import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.repositories.VehiclesRepository
import com.anum.locdagger.repositories.VehiclesRepositoryImpl
import com.anum.locdagger.service.api.LocationService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class VehicleFragmentModule {

    @FragmentScope
    @Provides fun getVehicleListPresenter(repository: VehiclesRepository, compositeDisposable: CompositeDisposable,
                                          appSchedulerProvider: AppSchedulerProvider
    )
            : VehicleFragmentContract.Presenter =
        VehicleFragmentPresenterImpl(repository, compositeDisposable, appSchedulerProvider)

    @FragmentScope
    @Provides fun getVehicleLocationAdapter(itemClickListener: VehicleFragment) : VehicleLocationAdapter =
        VehicleLocationAdapter(mutableListOf(), itemClickListener)

    @FragmentScope
    @Provides fun getCompositeDisposible() : CompositeDisposable = CompositeDisposable()

    @FragmentScope
    @Provides fun getVehiclesRepository(locationService: LocationService) : VehiclesRepository =
        VehiclesRepositoryImpl(locationService)
}