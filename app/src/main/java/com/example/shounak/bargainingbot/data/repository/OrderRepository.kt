package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.db.entity.Order

/**
 * Created by Shounak on 12-Feb-19
 */
interface OrderRepository {
    suspend fun getFoodCart(): LiveData<List<FoodCartOrder>>
    suspend fun addItemToFoodCart(time: Long, name: String, quantity: Int, cost: Int)
    suspend fun addCartToOrders(cartList: List<FoodCartOrder>)
    suspend fun getOrders(): LiveData<List<Order>>
    suspend fun clearFoodCart()
    suspend fun clearOrders()
}