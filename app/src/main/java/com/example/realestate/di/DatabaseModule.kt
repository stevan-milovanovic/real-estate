package com.example.realestate.di

import android.app.Application
import androidx.room.Room
import com.example.realestate.db.FavouriteDao
import com.example.realestate.db.PropertyDao
import com.example.realestate.db.PropertyDetailsDao
import com.example.realestate.db.RealEstateDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): RealEstateDb {
        return Room.databaseBuilder(app, RealEstateDb::class.java, "real-estate")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePropertyDao(db: RealEstateDb): PropertyDao = db.propertyDao()

    @Singleton
    @Provides
    fun providesFavouriteDao(db: RealEstateDb): FavouriteDao = db.favouriteDao()

    @Singleton
    @Provides
    fun providesPropertyDetailsDao(db: RealEstateDb): PropertyDetailsDao = db.propertyDetailsDao()

}