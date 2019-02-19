package com.example.shounak.bargainingbot.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.BotRepository
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository

/**
 * Created by Shounak on 02-Feb-19
 */
class MainActivityViewModelFactory(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val botRepository: BotRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(userRepository, orderRepository, botRepository) as T
    }
}