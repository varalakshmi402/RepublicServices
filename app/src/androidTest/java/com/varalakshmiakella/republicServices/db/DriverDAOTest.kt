package com.varalakshmiakella.republicServices.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.google.common.truth.Truth.assertThat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.varalakshmiakella.republicServices.getOrAwaitValue
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.model.Route
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DriverDAOTest {
    @JvmField
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: DriverDatabase
    private lateinit var dao:DriverDAO

    @Before
    fun setUp(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DriverDatabase::class.java,
        ).allowMainThreadQueries().build()
        dao=database.getDriverDao()
    }
    @After
    fun tearDown(){
        database.close()
    }
    @Test
    fun upsertDriver()= runTest{
        val driverName = listOf(Driver(12,"20","Philipp Lackner"))
        dao.upsertDriver(driverName)
        val allDriverNames = dao.getAllDriverList().getOrAwaitValue()
        assertThat(allDriverNames).isEqualTo(driverName)
    }

    @Test
    fun upsertRoute()= runTest{
        val routeDetails = listOf(Route(12,"North Side","I"))
        dao.upsertRoute(routeDetails)
        val allDriverNames = dao.getAllRoutesList().getOrAwaitValue()
        assertThat(allDriverNames).isEqualTo(allDriverNames)
    }

    @Test
    fun getRouteOfDriverWithMatchID()=runTest{
        val driverName = listOf(Driver(20,"17","Philipp Lackner"))
        val routeDetails = listOf(
            Route(13,"North Side","I"),
            Route(17,"South Side","W"),
            Route(1,"West Side","R"),
            Route(15,"East Side","R"),
        Route(17,"South Side","W"))
        dao.upsertDriver(driverName)
        dao.upsertRoute(routeDetails)
        val routeName=dao.getRouteOfDriver("17").name
        assertThat(routeName).isEqualTo("South Side")
    }

    @Test
    fun getRouteOfDriverWithEvenID()=runTest{
        val driverName = listOf(Driver(20,"16","Philipp Lackner"))
        val routeDetails = listOf(
            Route(13,"North Side","I"),
            Route(17,"South Side","W"),
            Route(1,"West Side","R"),
            Route(15,"East Side","R")
        )
        dao.upsertDriver(driverName)
        dao.upsertRoute(routeDetails)
        val routeName=dao.getRouteOfDriver("16").name
        assertThat(routeName).isEqualTo("West Side")
    }

    @Test
    fun getRouteOfDriverWithDivideBy5ID()=runTest{
        val driverName = listOf(Driver(20,"15","Philipp Lackner"))
        val routeDetails = listOf(
            Route(13,"North Side","I"),
            Route(17,"South Side","W"),
            Route(1,"West Side","C"),
            Route(10,"East Side","C")
        )
        dao.upsertDriver(driverName)
        dao.upsertRoute(routeDetails)
        val routeName=dao.getRouteOfDriver("15").name
        assertThat(routeName).isEqualTo("East Side")
    }

    @Test
    fun getRouteOfDriverWhereIDDoesNotExistInRoute()=runTest{
        val driverName = listOf(Driver(20,"19","Philipp Lackner"))
        val routeDetails = listOf(
            Route(13,"North Side","I"),
            Route(17,"South Side","W"),
            Route(1,"West Side","I"),
            Route(18,"East Side","I")
        )
        dao.upsertDriver(driverName)
        dao.upsertRoute(routeDetails)
        val routeName=dao.getRouteOfDriver("19").name
        assertThat(routeName).isEqualTo("East Side")
    }
}