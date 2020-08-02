package com.example.realestate.api

import com.example.realestate.model.Property

data class PropertyNetworkResponse(
    val items: List<Property>
)