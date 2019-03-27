package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shounak.bargainingbot.internal.MessageFrom

/**
 * Message Entity for local Room database
 */

@Entity
data class Message(
    @PrimaryKey
    val timeMillis: Long,
    val message: String,
    val from: MessageFrom
) {

}