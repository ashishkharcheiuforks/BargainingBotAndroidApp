package com.example.shounak.bargainingbot.ui.main.food

import androidx.lifecycle.ViewModel;
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class FoodMenuViewModel(menuRepository: MenuRepository) : ViewModel() {

    val food by lazyDeferred {
        return@lazyDeferred menuRepository.getFoodMenu()
    }

}
