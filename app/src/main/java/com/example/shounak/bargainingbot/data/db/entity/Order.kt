package com.example.shounak.bargainingbot.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shounak.bargainingbot.data.db.OrderType

@Entity(tableName = "CurrentOrders")
data class Order(

    val time : Long,
    val type : OrderType,
    @PrimaryKey
    val name: String,
    val quantity: Int,
    val cost: Int
)