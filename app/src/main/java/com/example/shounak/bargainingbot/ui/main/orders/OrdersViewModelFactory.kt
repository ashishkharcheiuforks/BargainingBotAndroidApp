package com.example.shounak.bargainingbot.ui.main.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository

/**
 * View model factory to provide Orders view model
 */
class OrdersViewModelFactory(
    private val ordersRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(ordersRepository, userRepository) as T
    }
}