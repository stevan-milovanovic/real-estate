package com.example.realestate.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.realestate.model.PropertyDetails

@Dao
interface PropertyDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(propertyDetails: PropertyDetails)

    @Query("SELECT * FROM PropertyDetails WHERE advertisementId = :advertisementId")
    fun getPropertyDetails(advertisementId: Long): LiveData<PropertyDetails>

}