package com.anum.locdagger.repositories

import com.anum.locdagger.models.Fleets
import com.anum.locdagger.service.api.LocationService
import io.reactivex.Single
import javax.inject.Inject

class VehiclesRepositoryImpl @Inject constructor(var locationService : LocationService) : VehiclesRepository {

    override fun getVehiclesList(
        url: String,
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Single<Fleets> = locationService.getVehicles(url, latitude1, longitude1, latitude2, longitude2)

}