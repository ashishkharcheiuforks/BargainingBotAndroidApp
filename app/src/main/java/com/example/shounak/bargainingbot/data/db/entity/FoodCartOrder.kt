package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Shounak on 12-Feb-19
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