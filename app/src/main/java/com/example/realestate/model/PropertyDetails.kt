package com.example.realestate.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PropertyDetails(
    @PrimaryKey
    @field:SerializedName("advertismentId")
    val advertisementId: Long,
    val title: String,
    val propertyStreet: String,
    @field:SerializedName("propertyCityname")
    val propertyCityName: String,
    val propertyCountry: String,
    val realEstatePictures: List<RealEstatePicture> = listOf()
) {
    data class RealEstatePicture(
        val url: String
    )
}