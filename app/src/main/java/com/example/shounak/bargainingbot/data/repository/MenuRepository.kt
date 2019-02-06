package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food

/**
 * Created by Shounak on 06-Feb-19
 */
interface MenuRepository {

    suspend fun getDrinksMenu(): LiveData<List<Drinks>>

    suspend fun getFoodMenu(): LiveData<List<Food>>

}