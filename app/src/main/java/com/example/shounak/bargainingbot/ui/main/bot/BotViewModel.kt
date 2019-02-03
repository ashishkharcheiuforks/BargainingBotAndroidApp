package com.example.shounak.bargainingbot.ui.main.bot

import androidx.lifecycle.ViewModel;
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class BotViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    val user by lazyDeferred {
        userRepository.getCurrentUser()
    }

}
