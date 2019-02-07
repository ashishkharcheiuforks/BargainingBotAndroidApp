package com.example.shounak.bargainingbot.ui.main.bot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.data.repository.UserRepository

/**
 * Created by Shounak on 02-Feb-19
 */
class BotViewModelFactory(private val userRepository: UserRepository, private val apiaiService : APIAIService) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BotViewModel(userRepository, apiaiService ) as T
    }

}