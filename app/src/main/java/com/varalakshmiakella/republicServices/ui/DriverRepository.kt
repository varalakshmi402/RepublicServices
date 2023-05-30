package com.varalakshmiakella.republicServices.ui

import com.varalakshmiakella.republicServices.api.RetrofitInstance
import com.varalakshmiakella.republicServices.db.DriverDatabase
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.model.Route

class DriverRepository(private val db: DriverDatabase) {

    suspend fun getDriverData()= RetrofitInstance.api.getDriverList()

    suspend fun upsertDriver(driver: List<Driver>) = db.getDriverDao().upsertDriver(driver)

    suspend fun upsertRoute(route: List<Route>) = db.getDriverDao().upsertRoute(route)

    suspend fun getRouteData(id: String)= db.getDriverDao().getRouteOfDriver(id)

}