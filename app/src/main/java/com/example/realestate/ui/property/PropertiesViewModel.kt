package com.example.realestate.ui.property

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.realestate.model.Property
import com.example.realestate.repository.PropertyRepository
import javax.inject.Inject

class PropertiesViewModel @Inject constructor(private val propertyRepository: PropertyRepository) :
    ViewModel() {

    val properties = propertyRepository.loadProperties()

    val favourites = Transformations.switchMap(propertyRepository.loadFavourites()) {
        propertyRepository.loadProperties()
    }

    fun toggleFavorite(property: Property) {
        propertyRepository.toggleFavourite(property)
    }

}