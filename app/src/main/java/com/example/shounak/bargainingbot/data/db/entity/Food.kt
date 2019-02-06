package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Shounak on 06-Feb-19
 */

@Entity
data class Food(
    val type: String,
    @PrimaryKey
    val name: String,
    val cost: String,
    val description: String
)