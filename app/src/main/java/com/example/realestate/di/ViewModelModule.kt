package com.example.realestate.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.realestate.ui.property.PropertiesViewModel
import com.example.realestate.ui.property.details.PropertyDetailsViewModel
import com.example.realestate.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PropertiesViewModel::class)
    abstract fun bindPropertiesViewModel(propertiesViewModel: PropertiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PropertyDetailsViewModel::class)
    abstract fun bindPropertyDetailsViewModel(propertyDetailsViewModel: PropertyDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

}