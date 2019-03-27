package com.example.shounak.bargainingbot.ui.main.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.data.repository.OrderRepository

/**
 * View model factory to provide Food menu view model
 */
class FoodMenuViewModelFactory(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FoodMenuViewModel(menuRepository, orderRepository) as T
    }

}