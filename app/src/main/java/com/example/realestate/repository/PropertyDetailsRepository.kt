package com.example.realestate.repository

import androidx.lifecycle.LiveData
import com.example.realestate.AppExecutors
import com.example.realestate.api.PropertyService
import com.example.realestate.db.PropertyDetailsDao
import com.example.realestate.model.PropertyDetails
import com.example.realestate.model.base.Resource
import com.example.realestate.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class PropertyDetailsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val propertyDetailsDao: PropertyDetailsDao,
    private val propertyService: PropertyService
) {

    fun loadPropertyDetails(advertisementId: Long): LiveData<Resource<PropertyDetails>> {
        return object : NetworkBoundResource<PropertyDetails, PropertyDetails>(appExecutors) {
            override fun saveCallResult(item: PropertyDetails) {
                propertyDetailsDao.insert(item)
            }

            override fun shouldFetch(data: PropertyDetails?) = data == null

            override fun loadFromDb() = propertyDetailsDao.getPropertyDetails(advertisementId)

            override fun createCall() = propertyService.getProperty(advertisementId)

        }.asLiveData()
    }

}