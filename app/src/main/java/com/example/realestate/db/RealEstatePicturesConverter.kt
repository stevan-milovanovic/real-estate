package com.example.realestate.db

import androidx.room.TypeConverter
import com.example.realestate.model.PropertyDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RealEstatePicturesConverter {

    private val gson = Gson()
    private val type = genericType<List<PropertyDetails.RealEstatePicture>>()

    @TypeConverter
    fun fromRealEstatePictures(realEstatePictures: List<PropertyDetails.RealEstatePicture>): String {
        return gson.toJson(realEstatePictures, type)
    }

    @TypeConverter
    fun toRealEstatePictures(dbValue: String): List<PropertyDetails.RealEstatePicture> {
        return gson.fromJson(dbValue, type)
    }

    private inline fun <reified T> genericType(): Type = object : TypeToken<T>() {}.type

}