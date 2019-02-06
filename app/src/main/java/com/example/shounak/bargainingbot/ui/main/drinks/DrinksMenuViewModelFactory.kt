package com.example.shounak.bargainingbot.ui.main.drinks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shounak.bargainingbot.data.repository.MenuRepository

/**
 * Created by Shounak on 06-Feb-19
 */
class DrinksMenuViewModelFactory(private val menuRepository : MenuRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinksMenuViewModel(menuRepository) as T
    }

}