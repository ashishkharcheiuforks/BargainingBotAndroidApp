package com.example.shounak.bargainingbot.ui.main.bot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.data.repository.BotRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository

/**
 * View model factory to provide Bot view model
 */
class BotViewModelFactory(
    private val userRepository: UserRepository,
    private val apiaiService: APIAIService,
    private val botRepository: BotRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BotViewModel(userRepository, apiaiService, botRepository) as T
    }

}