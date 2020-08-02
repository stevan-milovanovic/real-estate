package com.example.realestate.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourite(
    @PrimaryKey
    val id: Long
)