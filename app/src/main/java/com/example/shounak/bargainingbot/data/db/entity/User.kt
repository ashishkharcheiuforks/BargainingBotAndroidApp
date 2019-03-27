package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User Entity for local Room database
 * User table only contains one entry that of the currently signed in user.
 */

const val CURRENT_USER_ID: Int = 0

@Entity
data class User(
    val uid : String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName : String = "",
    val regular: Boolean = false,
    val photoUrl: String= ""
) {



    @PrimaryKey(autoGenerate = false)
    var constId: Int = CURRENT_USER_ID
}