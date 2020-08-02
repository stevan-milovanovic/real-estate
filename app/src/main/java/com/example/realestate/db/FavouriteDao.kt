package com.example.realestate.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.realestate.model.Favourite

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favourite: Favourite)

    @Delete
    fun delete(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    fun getAll(): LiveData<List<Favourite>>

}