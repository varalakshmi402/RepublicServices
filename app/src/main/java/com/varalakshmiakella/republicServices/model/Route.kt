package com.varalakshmiakella.republicServices.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "route")
data class Route(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String?
)