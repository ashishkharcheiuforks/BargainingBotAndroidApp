package com.example.shounak.bargainingbot.ui.main.orders

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.Order
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.util.*

class OrdersViewModel(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val orders by lazyDeferred {
        orderRepository.getOrders()
    }

    val isDrinksLoadingCompleted = orderRepository.isDrinksLoadingCompleted


    suspend fun checkoutWithUserId(userId: String?, orderList: List<Order>?, orderTotal : Int) {
        if (!orderList.isNullOrEmpty() and !userId.isNullOrBlank()) {
            val orderJson = Gson().toJson(orderList)
            val mainDocument = HashMap<String, Any>()
            mainDocument["time"] = Date().time
            mainDocument["Total"] = orderTotal
            mainDocument["userId"] = userId!!
            mainDocument["Order"] = orderJson

            var user: User? = null
            runBlocking {
                user = userRepository.getCurrentUserLocal()

                withContext(Dispatchers.IO) {
                    // TODO: Using user!!
                    val checkOut = async { orderRepository.checkOut(mainDocument, user!!) }
//                val sendMail = async { orderRepository.sendConfirmationMail() }
                    awaitAll(checkOut)
                }
            }


        }
    }

    suspend fun clearOrders(userId: String) {
        withContext(Dispatchers.IO) {
            orderRepository.clearOrders(userId)
        }
    }

    suspend fun clearLocalOrders() {
        orderRepository.clearLocalOrders()
    }
}
