package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.TestSchedulerProvider
import com.anum.locdagger.listeners.SchedulerProvider
import com.anum.locdagger.models.Coordinate
import com.anum.locdagger.models.Fleets
import com.anum.locdagger.models.VehicleLocation
import com.anum.locdagger.repositories.VehiclesRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@RunWith(MockitoJUnitRunner::class)
class VehicleFragmentPresenterImplTest {

    @Mock private lateinit var view : VehicleFragmentContract.View
    @Mock private lateinit var vehiclesRepository: VehiclesRepository
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var schedulerProvider: SchedulerProvider = TestSchedulerProvider()
    private lateinit var presenter: VehicleFragmentPresenterImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = VehicleFragmentPresenterImpl(vehiclesRepository, compositeDisposable, schedulerProvider)
        presenter.attachView(view)
    }

    @Test
    fun testGetVehicles_Success_fleetListWithItems() {

        val vehicle = VehicleLocation(1, "TAXI", 0.0f, Coordinate(24.0, 57.0), null)
        val single = Single.create { emitter : SingleEmitter<Fleets> ->
            val list : List<VehicleLocation> = listOf(vehicle)
            val fleet = Fleets(list)
            emitter.onSuccess(fleet)
        }
        whenever(vehiclesRepository.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)
//        whenever(view.getAddressFromLocation(vehicle)).thenReturn(Single.just(vehicle))
        presenter.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891)
        verify(view).showLoader()
        verify(view).hideLoader()
        verify(view).isLocationWithinUserBounds(vehicle)
//        verify(view).getAddressFromLocation(vehicle)
        verify(view).populateData(ArgumentMatchers.anyList())
    }

    @Test
    fun testGetVehicles_Success_fleetListEmpty() {

        val single = Single.create { emitter : SingleEmitter<Fleets> ->
            val list : List<VehicleLocation> = listOf()
            val fleet = Fleets(list)
            emitter.onSuccess(fleet)
        }
        whenever(vehiclesRepository.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)
        presenter.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891)
        verify(view).showLoader()
        verify(view).hideLoader()
        verify(view).populateData(ArgumentMatchers.anyList())
    }

    @Test
    fun testGetVehicles_Success_Null_Fleet_List() {

        val single = Single.create { emitter : SingleEmitter<Fleets> ->
            val fleet = Fleets(null)
            emitter.onSuccess(fleet)
        }
        whenever(vehiclesRepository.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)
        presenter.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891)
        verify(view).showLoader()
        verify(view).hideLoader()
        verify(view).showError("No data found")
    }

    @Test
    fun testGetVehicles_Failure() {

        val single = Single.create { emitter : SingleEmitter<Fleets> ->
            emitter.onError(Exception("No data found"))
        }
        whenever(vehiclesRepository.getVehiclesList("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891))
            .thenReturn(single)
        presenter.getVehicles("https://test-url", 53.694865, 9.757589, 53.394655, 10.099891)
        verify(view).showLoader()
        verify(view).hideLoader()
        verify(view).showError("No data found")
    }


    @After
    fun tearDown() {
        presenter.detachView()
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}