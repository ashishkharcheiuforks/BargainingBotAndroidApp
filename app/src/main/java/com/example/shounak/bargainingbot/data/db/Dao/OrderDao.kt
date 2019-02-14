package com.example.shounak.bargainingbot.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shounak.bargainingbot.data.db.OrderType
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.db.entity.Order

/**
 * Created by Shounak on 12-Feb-19
 */
@Dao
interface OrderDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrder(order : Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFoodOrderToCart(foodCartOrder: FoodCartOrder)

    @Query("select * from CurrentOrders")
    fun getOrders() : LiveData<List<Order>>

    @Query("select * from CurrentOrders where name = :name")
    fun findInOrders(name : String) : Order?

    @Query("select * from FoodCartOrder")
    fun getFoodCart() : LiveData<List<FoodCartOrder>>

    @Query("select * from FoodCartOrder where name = :name")
    fun findInFoodCart(name : String) : FoodCartOrder?

    @Query("delete from FoodCartOrder")
    fun clearFoodCart()

    @Query("delete from CurrentOrders")
    fun clearOrders()

    @Query("delete from FoodCartOrder where name = :name")
    fun deleteItemFromFoodCart(name : String)

    @Query("delete from CurrentOrders where type = :type")
    fun deleteDrinksOrder(type : OrderType)




}