package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food

/**
 * Menu repository interface
 */
interface MenuRepository {

    suspend fun getDrinksMenu(): LiveData<List<Drinks>>

    suspend fun getFoodMenu(): LiveData<List<Food>>

    suspend fun getDrinksListByType(title: String): List<Drinks>
    suspend fun getFoodListByType(type: String): List<Food>
}