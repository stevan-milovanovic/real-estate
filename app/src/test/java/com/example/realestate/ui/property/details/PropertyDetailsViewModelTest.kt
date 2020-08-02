package com.example.realestate.ui.property.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.realestate.model.PropertyDetails
import com.example.realestate.model.base.Resource
import com.example.realestate.repository.PropertyDetailsRepository
import com.example.realestate.testing.mock
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class PropertyDetailsViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: PropertyDetailsRepository = mock()
    private val viewModel = PropertyDetailsViewModel(repository)

    @Test
    fun testInitialState() {
        assertNotNull(viewModel.propertyDetails)
        assertNull(viewModel.propertyDetails.value)
        assertFalse(viewModel.propertyDetails.hasObservers())
    }

    @Test
    fun doNotFetchWithoutObservers() {
        viewModel.setAdvertisementId(1L)
        verify(repository, never()).loadPropertyDetails(1L)
    }

    @Test
    fun fetchWhenObserved() {
        viewModel.setAdvertisementId(1L)
        viewModel.propertyDetails.observeForever(mock())
        verify(repository).loadPropertyDetails(1L)
    }

    @Test
    fun changeWhileObserved() {
        viewModel.propertyDetails.observeForever(mock())

        viewModel.setAdvertisementId(1L)
        viewModel.setAdvertisementId(2L)

        verify(repository).loadPropertyDetails(1L)
        verify(repository).loadPropertyDetails(2L)
    }

    @Test
    fun testLoadPropertyDetails() {
        viewModel.propertyDetails.observeForever(mock())
        viewModel.setAdvertisementId(1L)
        verify(repository).loadPropertyDetails(1L)
        reset(repository)
        viewModel.setAdvertisementId(2L)
        verify(repository).loadPropertyDetails(2L)
    }

    @Test
    fun testLoadSamePropertyDetailsTwice() {
        viewModel.propertyDetails.observeForever(mock())
        viewModel.setAdvertisementId(1L)
        verify(repository).loadPropertyDetails(1L)
        reset(repository)
        viewModel.setAdvertisementId(1L)
        verify(repository, never()).loadPropertyDetails(ArgumentMatchers.anyLong())
    }

    @Test
    fun testSendResultToUI() {
        val firstPropertyDetails = MutableLiveData<Resource<PropertyDetails>>()
        val secondPropertyDetails = MutableLiveData<Resource<PropertyDetails>>()

        `when`(repository.loadPropertyDetails(1L)).thenReturn(firstPropertyDetails)
        `when`(repository.loadPropertyDetails(2L)).thenReturn(secondPropertyDetails)

        val observer = mock<Observer<Resource<PropertyDetails>>>()
        viewModel.propertyDetails.observeForever(observer)
        viewModel.setAdvertisementId(1L)
        verify(observer, never()).onChanged(any())
        val firstPropertyDetailsValue =
            Resource.success(PropertyDetails(1L, "", "", "", "", listOf()))

        firstPropertyDetails.value = firstPropertyDetailsValue
        verify(observer).onChanged(firstPropertyDetailsValue)

        reset(observer)

        val secondPropertyDetailsValue =
            Resource.success(PropertyDetails(2L, "", "", "", "", listOf()))
        secondPropertyDetails.value = secondPropertyDetailsValue
        verify(observer, never()).onChanged(any())
        viewModel.setAdvertisementId(2L)
        verify(observer).onChanged(secondPropertyDetailsValue)
        verifyNoMoreInteractions(observer)
    }

}