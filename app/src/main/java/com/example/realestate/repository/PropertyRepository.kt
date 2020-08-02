package com.example.realestate.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.realestate.AppExecutors
import com.example.realestate.api.PropertyNetworkResponse
import com.example.realestate.api.PropertyService
import com.example.realestate.db.FavouriteDao
import com.example.realestate.db.PropertyDao
import com.example.realestate.model.Favourite
import com.example.realestate.model.Property
import com.example.realestate.model.base.Resource
import com.example.realestate.testing.OpenForTesting
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class PropertyRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val propertyDao: PropertyDao,
    private val favouriteDao: FavouriteDao,
    private val propertyService: PropertyService
) {

    fun loadProperties(): LiveData<Resource<List<Property>>> {
        return object :
            NetworkBoundResource<List<Property>, PropertyNetworkResponse>(appExecutors) {
            override fun saveCallResult(item: PropertyNetworkResponse) {
                propertyDao.insert(item.items)
            }

            override fun shouldFetch(data: List<Property>?) = data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<Property>> {
                val properties = propertyDao.getAll()
                val favouritesLiveData = favouriteDao.getAll()

                Transformations.map(properties) { propertiesWithoutFavInfo ->
                    propertiesWithoutFavInfo.forEach { property ->
                        property.favourite =
                            favouritesLiveData.value!!.contains(Favourite(property.advertisementId))
                    }
                }

                return properties
            }

            override fun createCall() = propertyService.getProperties()
        }.asLiveData()
    }

    fun loadFavourites() = favouriteDao.getAll()

    fun toggleFavourite(property: Property) {
        appExecutors.diskIO().execute {
            property.favourite = !property.favourite
            propertyDao.updateFavourite(property)

            if (property.favourite) {
                favouriteDao.insert(Favourite(property.advertisementId))
            } else {
                favouriteDao.delete(Favourite(property.advertisementId))
            }
        }
    }

}