package com.varalakshmiakella.republicServices.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.model.Route

@Dao
interface DriverDAO {
    @Query("SELECT * FROM driver")
    fun getAllDriverList():LiveData<List<Driver>>

    @Query("SELECT * FROM route")
    fun getAllRoutesList():LiveData<List<Route>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDriver(driver: List<Driver>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRoute(route: List<Route>)

    @Query("SELECT * FROM " +
        "(SELECT  d.id id , CASE " +
        "WHEN d.id = r.id THEN r.type " +
        "WHEN d.id % 2 = 0 THEN 'R' " +
        "WHEN d.id % 5 = 0 THEN 'C'" +
        "ELSE 'I'END AS type," +
        "CASE WHEN d.id = r.id THEN r.name " +
        "WHEN d.id % 2 = 0 THEN (SELECT name FROM route WHERE id = (SELECT MIN(id) FROM route WHERE type = 'R'))" +
        "WHEN d.id % 5 = 0 then (SELECT name FROM route WHERE id = (SELECT id  FROM route WHERE type = 'C' ORDER BY id LIMIT 1 OFFSET 1))" +
        "ELSE (SELECT name FROM route WHERE id = (SELECT MAX(id) FROM route WHERE type = 'I'))" +
        "END AS name FROM driver d " +
        "LEFT JOIN route r ON d.id = r.id ) driverRoute WHERE driverRoute.id = :id")
    suspend fun getRouteOfDriver(id: String): Route
}