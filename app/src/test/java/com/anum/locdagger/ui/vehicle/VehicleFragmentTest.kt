package com.anum.locdagger.ui.vehicle

import com.anum.locdagger.ui.main.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VehicleFragmentTest {

    lateinit var activity: MainActivity
    lateinit var fragment: VehicleFragment

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun testActivityNotNull() {
        assertNotNull(activity)
    }
}