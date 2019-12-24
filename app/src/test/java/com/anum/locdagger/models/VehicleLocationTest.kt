package com.anum.locdagger.models

import org.junit.Test

import org.junit.Assert.*

class VehicleLocationTest {

    @Test
    fun assertFleetIsTaxiUppercase() {
        var vehicleLocation = VehicleLocation(1, "TAXI", 2.333f, Coordinate(34.323, 23.333), "")
        assertEquals(vehicleLocation.isFleetTaxi(), true)
    }

    @Test
    fun assertFleetIsTaxiLowercase() {
        var vehicleLocation = VehicleLocation(1, "taxi", 2.333f, Coordinate(34.323, 23.333), "")
        assertEquals(vehicleLocation.isFleetTaxi(), true)
    }

    @Test
    fun assertFleetNotTaxi() {
        var vehicleLocation = VehicleLocation(1, "poi", 2.333f, Coordinate(34.323, 23.333), "")
        assertEquals(vehicleLocation.isFleetTaxi(), false)
    }

    @Test
    fun assertFleetNotTaxiWithEmpty() {
        var vehicleLocation = VehicleLocation(1, "", 2.333f, Coordinate(34.323, 23.333), "")
        assertEquals(vehicleLocation.isFleetTaxi(), false)
    }

    @Test
    fun assertCoordinatesExist() {
        var vehicleLocation = VehicleLocation(1, "poi", 2.333f,
            Coordinate(34.323, 23.333), "")
        assertNotNull(vehicleLocation.coordinate)
    }
}