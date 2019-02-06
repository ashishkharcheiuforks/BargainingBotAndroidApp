package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Shounak on 06-Feb-19
 */

private const val ID_INITIALIZER: Int = 0

@Entity
data class Drinks(
    val type : String,
    @PrimaryKey(autoGenerate = false)
    val name : String,
    val cost : String
)