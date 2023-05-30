package com.varalakshmiakella.republicServices.api

import com.varalakshmiakella.republicServices.model.DriverRoute
import retrofit2.Response
import retrofit2.http.GET

interface DriverAPI {
    @GET("data")
    suspend fun getDriverList(): Response<DriverRoute>
}