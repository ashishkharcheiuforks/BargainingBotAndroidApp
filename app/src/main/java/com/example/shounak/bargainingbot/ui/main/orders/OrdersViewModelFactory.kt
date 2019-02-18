package com.example.shounak.bargainingbot.ui.main.orders

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository

/**
 * Created by Shounak on 13-Feb-19
 */
class OrdersViewModelFactory(
    private val ordersRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrdersViewModel(ordersRepository, userRepository, context) as T
    }
}