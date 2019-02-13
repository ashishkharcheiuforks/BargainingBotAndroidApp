package com.example.shounak.bargainingbot.ui.main.food.cart

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class FoodCartViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {


    val cartItems by lazyDeferred {
        orderRepository.getFoodCart()
    }

    suspend fun addCartToOrders(cartList : List<FoodCartOrder>){
        orderRepository.addCartToOrders(cartList)
    }

    suspend fun clearFoodCart() {
        orderRepository.clearFoodCart()
    }

}
