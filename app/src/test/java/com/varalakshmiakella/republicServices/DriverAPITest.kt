package com.varalakshmiakella.republicServices

import com.varalakshmiakella.republicServices.api.DriverAPI
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DriverAPITest {
lateinit var mockWebServer : MockWebServer
lateinit var driverAPI :DriverAPI
    @Before
    fun setUp() {
        mockWebServer= MockWebServer()
        driverAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DriverAPI::class.java)

    }

    @Test
   fun testBlankResponse() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}")
        mockWebServer.enqueue(mockResponse)
        driverAPI.getDriverList().body()
        mockWebServer.takeRequest()
        Assert.assertEquals("","")
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }


}