package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Food Entity for local Room database
 */

@Entity
data class Food(
    val type: String,
    @PrimaryKey
    val name: String,
    val cost: String,
    val description: String
)