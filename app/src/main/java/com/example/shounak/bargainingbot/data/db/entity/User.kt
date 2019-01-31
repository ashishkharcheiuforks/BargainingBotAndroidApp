package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_USER_ID: Int = 0

@Entity
data class User(
    val uid : String,
    val email: String?,
    val firstName: String,
    val isRegular: Boolean,
    val photoUrl: String
) {
    @PrimaryKey(autoGenerate = false)
    var constId: Int = CURRENT_USER_ID
}