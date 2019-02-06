package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food

/**
 * Created by Shounak on 06-Feb-19
 */
interface MenuNetworkDataSource {

    val downloadedDrinksMenu : LiveData<ArrayList<Drinks>>
    val downloadedFoodMenu : LiveData<ArrayList<Food>>

    fun getDrinksMenu()
    fun getFoodMenu()

}