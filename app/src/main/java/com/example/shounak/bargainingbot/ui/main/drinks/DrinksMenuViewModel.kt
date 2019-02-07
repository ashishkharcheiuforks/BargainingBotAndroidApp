package com.example.shounak.bargainingbot.ui.main.drinks

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class DrinksMenuViewModel(menuRepository: MenuRepository) : ViewModel() {

    val drinks by lazyDeferred {
        return@lazyDeferred menuRepository.getDrinksMenu()
    }
//
//    private lateinit var drinksList : LiveData<List<Drinks>>
//
//
//    fun getDrinksMenuTitles(list : List<Drinks>): ArrayList<String> {
//        return runBlocking {
//                val newList = list.distinctBy {
//                    it.type
//                }
//                val titleList: ArrayList<String> = ArrayList(10)
//                newList.forEach {
//                    titleList.add(it.type)
//                }
//                return@runBlocking titleList
//            }
//        }
//    }
//
//    suspend fun setupSingleTypeDrinkList(list: List<Drinks>){
//        withContext(Dispatchers.IO){
//            val beerList : ArrayList<Drinks> = ArrayList(10)
//            val wineList : ArrayList<Drinks> = ArrayList(10)
//            for (drink in list){
//                when{
//                    drink.type == "Beer" -> {
//                        beerList.add(drink)
//                    }
//                    drink.type == "Wine" -> {
//                        wineList.add(drink)
//                    }
//                }
//            }
//
//    }
//

}




