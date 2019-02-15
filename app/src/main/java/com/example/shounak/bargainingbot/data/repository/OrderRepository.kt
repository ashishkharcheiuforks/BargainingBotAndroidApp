package com.example.shounak.bargainingbot.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.db.entity.Order

/**
 * Created by Shounak on 12-Feb-19
 */
interface OrderRepository {
    suspend fun getFoodCart(): LiveData<List<FoodCartOrder>>
    suspend fun addItemToFoodCart(time: Long, name: String, quantity: Int, cost: Int)
    suspend fun addCartToOrders(cartList: List<FoodCartOrder>)
    suspend fun getOrders(context: Context): LiveData<List<Order>>
    suspend fun clearFoodCart()
    suspend fun deleteItemFromFoodCart(name: String)
    var isDrinksLoadingCompleted: MutableLiveData<Boolean>
    suspend fun checkOut(data: HashMap<String, Any>)
    suspend fun clearOrders(userId: String)
    suspend fun clearLocalOrders()
}