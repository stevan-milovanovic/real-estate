package com.example.realestate.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.realestate.model.Property

@Dao
interface PropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(properties: List<Property>)

    @Update
    fun updateFavourite(property: Property)

    @Query("SELECT * FROM property")
    fun getAll(): LiveData<List<Property>>

}