package com.example.realestate.ui.property.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestate.model.PropertyDetails
import com.example.realestate.model.base.Resource
import com.example.realestate.repository.PropertyDetailsRepository
import javax.inject.Inject

class PropertyDetailsViewModel @Inject constructor(
    private val propertyDetailsRepository: PropertyDetailsRepository
) : ViewModel() {

    private val advertisementId: MutableLiveData<Long> = MutableLiveData()

    val propertyDetails: LiveData<Resource<PropertyDetails>> =
        Transformations.switchMap(advertisementId) {
            propertyDetailsRepository.loadPropertyDetails(it)
        }

    fun setAdvertisementId(advertisementId: Long) {
        if (this.advertisementId.value == advertisementId) {
            return
        }

        this.advertisementId.value = advertisementId
    }

}