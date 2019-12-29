package com.anum.locdagger.repositories

import com.anum.locdagger.models.Coordinate
import com.anum.locdagger.models.Fleets
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.service.api.LocationService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class VehicleRepositoryImplTest  {

    lateinit var repo : VehiclesRepositoryImpl
    @Mock lateinit var locationService: LocationService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repo = VehiclesRepositoryImpl(locationService)
    }

    @Test
    fun testSimpleList() {

        val vehicle = VehicleLocation(1, "TAXI", 0.0f, Coordinate(24.0, 57.0), null)

        val single = Single.create<Fleets> {
            val list : List<VehicleLocation> = listOf(vehicle)
            val fleet = Fleets(list)
            it.onSuccess(fleet)
        }

        whenever(locationService.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)

        val testObserver = repo.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891).test()
        testObserver.assertNoErrors()
        testObserver.assertValue { fleets: Fleets -> fleets.list?.let {
            it.isNotEmpty()
        } ?: run {
            false
        } }
    }

    @Test
    fun testEmptyList() {

        val single = Single.create<Fleets> {
            val fleet = Fleets(listOf())
            it.onSuccess(fleet)
        }

        whenever(locationService.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)

        val testObserver = repo.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891).test()
        testObserver.assertNoErrors()
        testObserver.assertValue { fleets: Fleets -> fleets.list?.let {
            it.isEmpty()
        } ?: run {
            false
        } }

    }

}