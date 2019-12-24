package com.anum.locdagger.service.api

import com.anum.locdagger.models.Fleets
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LocationService {

    @GET
    fun getVehicles(@Url url: String,
                    @Query("p1Lat") latitude1: Double,
                    @Query("p1Lon") longitude1: Double,
                    @Query("p2Lat") latitude2: Double,
                    @Query("p2Lon") longitude2: Double)
            : Single<Fleets>
}