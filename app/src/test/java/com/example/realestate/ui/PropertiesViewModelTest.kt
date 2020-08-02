package com.example.realestate.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.realestate.model.Favourite
import com.example.realestate.model.Property
import com.example.realestate.model.base.Resource
import com.example.realestate.repository.PropertyRepository
import com.example.realestate.testing.mock
import com.example.realestate.ui.property.PropertiesViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class PropertiesViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: PropertyRepository
    private lateinit var viewModel: PropertiesViewModel

    private val propertiesLiveData = MutableLiveData<Resource<List<Property>>>()
    private val favouritesLiveData = MutableLiveData<List<Favourite>>()

    @Before
    fun setupB() {
        repository = mock()
        `when`(repository.loadProperties()).thenReturn(propertiesLiveData)
        `when`(repository.loadFavourites()).thenReturn(favouritesLiveData)
        viewModel =
            PropertiesViewModel(repository)
    }

    @Test
    fun testInitialState() {
        verify(repository).loadProperties()
        assertEquals(propertiesLiveData, viewModel.properties)

        verify(repository).loadFavourites()
        assertTrue(viewModel.favourites is MediatorLiveData)
    }

    @Test
    fun testToggleFavouriteWithoutObserver() {
        reset(repository)
        val property: Property = mock()
        viewModel.toggleFavorite(property)
        verify(repository).toggleFavourite(property)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun testToggleFavouriteWithObserver() {
        reset(repository)

        viewModel.properties.observeForever(mock())
        viewModel.favourites.observeForever(mock())
        val property: Property = mock()

        doAnswer {
            favouritesLiveData.postValue(mock())
        }.`when`(repository).toggleFavourite(property)

        viewModel.toggleFavorite(property)
        verify(repository).toggleFavourite(property)
        verify(repository).loadProperties()
    }

}