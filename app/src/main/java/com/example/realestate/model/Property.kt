package com.example.realestate.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.realestate.testing.OpenForTesting

@Entity
@OpenForTesting
data class Property(
    @PrimaryKey
    val advertisementId: Long,
    val title: String,
    val street: String,
    val city: String,
    val country: String,
    val price: Int,
    val currency: String,
    val picFilename1Small: String?
) {
    var favourite: Boolean = false
}