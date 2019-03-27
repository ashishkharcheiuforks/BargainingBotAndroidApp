package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shounak.bargainingbot.data.db.OrderType
import java.util.*

/**
 * Order Entity for local Room database
 */

@Entity(tableName = "CurrentOrders")
data class Order(

    val time : Long = Date().time,
    val tableNumber : Int,
    val type : OrderType,
    @PrimaryKey
    val name: String,
    val quantity: Int,
    val cost: Int
)