package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Food Cart Order Entity for local Room database
 */
@Entity
data class FoodCartOrder(
    val time : Long,
    @PrimaryKey
    val name : String,
    val quantity : Int,
    val cost : Int
){

}