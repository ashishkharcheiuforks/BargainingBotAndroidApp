package com.example.shounak.bargainingbot.ui.main.drinks

import androidx.lifecycle.ViewModel;
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class DrinksMenuViewModel(menuRepository: MenuRepository) : ViewModel() {

    val drinks by lazyDeferred {
        return@lazyDeferred menuRepository.getDrinksMenu()
    }

}
