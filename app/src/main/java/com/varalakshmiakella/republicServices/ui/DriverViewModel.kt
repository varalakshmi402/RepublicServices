package com.varalakshmiakella.republicServices.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varalakshmiakella.republicServices.model.Driver
import com.varalakshmiakella.republicServices.model.DriverRoute
import com.varalakshmiakella.republicServices.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DriverViewModel(private val driverRepository: DriverRepository):ViewModel() {

    val driverRoute: MutableLiveData<Resource<DriverRoute>> = MutableLiveData()
    val driver: MutableLiveData<Resource<Driver>> = MutableLiveData()
    var driverRouteResponse: DriverRoute? = null

    init {
        getDriverList()
    }

    private fun getDriverList() = viewModelScope.launch {
        driverRoute.postValue(Resource.Loading())
        val response = driverRepository.getDriverData()
        driverRoute.postValue(handleDriverRouteResponse(response))
    }

    suspend fun getRouteName(id: String): String {
        return driverRepository.getRouteData(id).name
    }

    suspend fun getRouteType(id: String): String {
        return driverRepository.getRouteData(id).type.toString()
    }

    private fun handleDriverRouteResponse(response:Response<DriverRoute>):Resource<DriverRoute>{
            if(response.isSuccessful){
                response.body()?.let{resultResponse->
                    viewModelScope.launch{
                        driverRepository.upsertDriver(resultResponse.drivers)
                        driverRepository.upsertRoute(resultResponse.routes)
                    }
                    return Resource.Success(driverRouteResponse?:resultResponse)
                }
            }
        return Resource.Error(response.message())
    }
}