package com.example.shounak.bargainingbot.ui.main.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.OrderRepository

/**
 * Created by Shounak on 13-Feb-19
 */
class OrdersViewModelFactory(
    private val ordersRepository: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(ordersRepository) as T
    }
}