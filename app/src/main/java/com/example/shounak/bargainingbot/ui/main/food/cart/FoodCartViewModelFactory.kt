package com.example.shounak.bargainingbot.ui.main.food.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.OrderRepository

/**
 * Created by Shounak on 12-Feb-19
 */
class FoodCartViewModelFactory(
    private val orderRepository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodCartViewModel(orderRepository) as T
    }
}