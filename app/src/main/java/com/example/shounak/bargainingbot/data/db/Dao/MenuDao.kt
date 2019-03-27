package com.example.shounak.bargainingbot.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food

/**
 * Menu Data Access Object
 */

@Dao
interface MenuDao {

    @Query("select * from Drinks order by type")
    fun getDrinksMenu(): LiveData<List<Drinks>>

    @Query("select * from Drinks where type = :type order by type")
    fun getDrinksListByType(type: String): List<Drinks>

    @Query("select * from Food order by type")
    fun getFoodMenu(): LiveData<List<Food>>

    @Query("select * from Food where type = :type order by type")
    fun getFoodListByType(type: String): List<Food>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDrinksMenu(drink: Drinks)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertFoodMenu(food: Food)


}