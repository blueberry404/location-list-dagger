package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.TestSchedulerProvider
import com.anum.locdagger.listeners.SchedulerProvider
import com.anum.locdagger.models.Fleets
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.service.api.LocationService
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class VehicleFragmentPresenterImplTest {

    @Mock private lateinit var view : VehicleFragmentContract.View
    @Mock private lateinit var locationService : LocationService
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var schedulerProvider: SchedulerProvider = TestSchedulerProvider()
    private lateinit var presenter: VehicleFragmentPresenterImpl

    @Before
    fun setUp() {
        presenter = VehicleFragmentPresenterImpl(locationService, compositeDisposable, schedulerProvider)
        presenter.attachView(view)
    }

    @Test
    fun testGetVehicles() {

        //mock
        var list  = mutableListOf<VehicleLocation>()
        list.add(mock(VehicleLocation::class.java))
        list.add(mock(VehicleLocation::class.java))
        list.add(mock(VehicleLocation::class.java))

        `when`(locationService.getVehicles(any(String::class.java), any(Double::class.java), any(Double::class.java), any(Double::class.java), any(Double::class.java)))
            .thenReturn(Single.just(Fleets(list)))

        //invoke
        presenter.getVehicles("", 1.1, 1.1, 1.1, 1.1)

        //assert
        verify(view).showLoader()
        verify(view).hideLoader()
    }


    /*
    @Test
    fun filterData_AllLocationsReturned() {

        var list  = mutableListOf<VehicleLocation>()
        list.add(mock(VehicleLocation::class.java))
        list.add(mock(VehicleLocation::class.java))
        list.add(mock(VehicleLocation::class.java))

//        `when`(list[0]).thenReturn(mock(VehicleLocation::class.java))
//        `when`(list[1]).thenReturn(mock(VehicleLocation::class.java))
//        `when`(list[2]).thenReturn(mock(VehicleLocation::class.java))

        `when`(view.isLocationWithinUserBounds(list[0])).thenReturn(true)
        `when`(view.isLocationWithinUserBounds(list[1])).thenReturn(true)
        `when`(view.isLocationWithinUserBounds(list[2])).thenReturn(true)

        var result = presenter.filterData(list)
//        verify(view).isLocationWithinUserBounds(mock(VehicleLocation::class.java))
//        verify(presenter).filterData(argThat { list -> list.size === 3 })

    }
    */

    @After
    fun tearDown() {
        presenter.detachView()
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}