package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Drinks Entity for local Room database
 */


@Entity
data class Drinks(
    val type : String,
    @PrimaryKey(autoGenerate = false)
    val name : String,
    val cost : String
)