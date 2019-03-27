package com.example.shounak.bargainingbot.ui.main.food.cart

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred
import java.util.*

/**
 * View model for Food Cart
 */

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

    suspend fun deleteItemFromFoodCart(item : FoodCartListItem){
       orderRepository.deleteItemFromFoodCart(item.name)
    }

    suspend fun addFoodOrderToCart(item: FoodCartListItem) {
        orderRepository.addItemToFoodCart(
            Date().time,
            item.name,
            item.quantity,
            item.cost
        )
    }

}
