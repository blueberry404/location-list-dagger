package com.anum.locdagger.di

import com.anum.locdagger.di.scope.FragmentScope
import com.anum.locdagger.ui.vehicle.VehicleFragment
import com.anum.locdagger.ui.vehicle.VehicleFragmentModule
import com.anum.locdagger.ui.vehiclesMap.VehicleMapModule
import com.anum.locdagger.ui.vehiclesMap.VehiclesMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules= [VehicleFragmentModule::class])
    abstract fun vehicleFragment() : VehicleFragment

    @FragmentScope
    @ContributesAndroidInjector(modules= [VehicleMapModule::class])
    abstract fun vehicleMapFragment() : VehiclesMapFragment
}