package com.example.realestate.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.realestate.model.Favourite
import com.example.realestate.model.Property
import com.example.realestate.model.PropertyDetails

@Database(
    entities = [Property::class, Favourite::class, PropertyDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RealEstatePicturesConverter::class)
abstract class RealEstateDb : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao

    abstract fun favouriteDao(): FavouriteDao

    abstract fun propertyDetailsDao(): PropertyDetailsDao

}