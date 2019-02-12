package com.example.shounak.bargainingbot.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.Dao.OrderDao
import com.example.shounak.bargainingbot.data.db.OrderType
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.db.entity.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderRepositoryImpl(
    private val orderDao: OrderDao
) : OrderRepository {


    override suspend fun getFoodCart(): LiveData<List<FoodCartOrder>> {
        return withContext(Dispatchers.IO) {
            orderDao.getFoodCart()
        }
    }

    override suspend fun addItemToFoodCart(time: Long, name: String, quantity: Int, cost: Int) {
        withContext(Dispatchers.IO) {
            val searchedEntry = orderDao.findInFoodCart(name)
            Log.d("additem", searchedEntry.toString())
            if(searchedEntry == null){
                orderDao.addFoodOrderToCart(
                    FoodCartOrder(time, name, quantity, cost)
                )
            }else{
                val newQuantity = searchedEntry.quantity + quantity
                val newCost = searchedEntry.cost + cost
                orderDao.addFoodOrderToCart(
                    FoodCartOrder(time,name, newQuantity, newCost)
                )
            }

        }
    }

    override suspend fun addCartToOrders(cartList : List<FoodCartOrder>){
       withContext(Dispatchers.IO){
           for (item in cartList){
               val searchedEntry = orderDao.findInOrders(item.name)
               if(searchedEntry == null){
                   orderDao.addOrder(Order(
                       item.time,
                       OrderType.FOOD,
                       item.name,
                       item.quantity,
                       item.cost
                   ))
               }else{
                   val newCost = searchedEntry.cost + item.cost
                   val newQuantity = searchedEntry.quantity + item.quantity
                   orderDao.addOrder(Order(
                       item.time,
                       OrderType.FOOD,
                       item.name,
                       newQuantity,
                       newCost
                   ))
               }

           }
       }

    }

}