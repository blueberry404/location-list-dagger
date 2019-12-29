package com.anum.locdagger.repositories

import com.anum.locdagger.models.Fleets
import io.reactivex.Single

interface VehiclesRepository {

    fun getVehiclesList(url: String,
                        latitude1: Double,
                        longitude1: Double,
                        latitude2: Double,
                        longitude2: Double)
            : Single<Fleets>
}