package com.example.shounak.bargainingbot.ui.main.drinks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * View model for Drinks menu
 */

class DrinksMenuViewModel(private val menuRepository: MenuRepository) : ViewModel() {

    val drinks by lazyDeferred {
        return@lazyDeferred menuRepository.getDrinksMenu()
    }
    //
    private lateinit var drinksList: LiveData<List<Drinks>>


    suspend fun getDrinksMenuTitles(list: List<Drinks>): ArrayList<String> {
        return withContext(Dispatchers.Default) {
            val newList = list.distinctBy {
                it.type
            }
            val titleList: ArrayList<String> = ArrayList(10)
            newList.forEach {
                titleList.add(it.type)
            }
            return@withContext titleList
        }
    }

    suspend fun getDrinksListByType(title : String) : List<Drinks> {
        return menuRepository.getDrinksListByType(title)
    }

}






