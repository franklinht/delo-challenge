package com.frank.delochallenge.model.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postal_code")
data class PostalCode(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val address: String,
    val postal_code: String
)
