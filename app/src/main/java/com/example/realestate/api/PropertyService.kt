package com.example.realestate.api

import androidx.lifecycle.LiveData
import com.example.realestate.model.PropertyDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyService {

    @GET("properties")
    fun getProperties(): LiveData<ApiResponse<PropertyNetworkResponse>>

    @GET("properties/{propertyId}")
    fun getProperty(@Path("propertyId") propertyId: Long): LiveData<ApiResponse<PropertyDetails>>

}