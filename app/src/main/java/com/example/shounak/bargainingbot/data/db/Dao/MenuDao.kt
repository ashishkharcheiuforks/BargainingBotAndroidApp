package com.example.shounak.bargainingbot.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food

/**
 * Created by Shounak on 06-Feb-19
 */

@Dao
interface MenuDao {

    @Query("select * from Drinks order by type")
    fun getDrinksMenu() : LiveData<List<Drinks>>


    @Query("select * from Food order by type")
    fun getFoodMenu() : LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDrinksMenu(drink: Drinks)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertFoodMenu(food: Food)




}