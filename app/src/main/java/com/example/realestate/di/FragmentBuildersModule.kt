package com.example.realestate.di

import com.example.realestate.ui.property.PropertiesFragment
import com.example.realestate.ui.property.details.PropertyDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributePropertiesFragment(): PropertiesFragment

    @ContributesAndroidInjector
    abstract fun contributePropertyDetailsFragment(): PropertyDetailsFragment
}