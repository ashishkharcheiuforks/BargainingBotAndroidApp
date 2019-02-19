package com.example.shounak.bargainingbot.ui.main.food

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class FoodMenuViewModel(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    val food by lazyDeferred {
        return@lazyDeferred menuRepository.getFoodMenu()
    }

    suspend fun getFoodMenuTitles(list: List<Food>): ArrayList<String> {
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

    suspend fun getFoodListByType(type: String) : List<Food>{
        return menuRepository.getFoodListByType(type)
    }

    suspend fun addOrderToCart(name: String, quantity : Int, cost: Int) {
        val time = Date().time
        val totalCost = cost * quantity

        orderRepository.addItemToFoodCart(time,name, quantity, totalCost)

    }

}
