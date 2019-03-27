package com.example.shounak.bargainingbot.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.Order
import com.example.shounak.bargainingbot.data.repository.BotRepository
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

/**
 * Main Activity view model
 */
class MainActivityViewModel(
   private val userRepository: UserRepository,
   private val orderRepository: OrderRepository,
   private val botRepository: BotRepository
) :  ViewModel() {


    var isTableSelected = MutableLiveData<Boolean>()

    init {
        isTableSelected.value = false
    }

   val user by lazyDeferred {
      userRepository.getCurrentUser()
   }

    val isDrinksLoadingCompleted = orderRepository.isDrinksLoadingCompleted as LiveData<Boolean>


    suspend fun getOrders(): LiveData<List<Order>> {
       return orderRepository.getOrders()
    }

    suspend fun clearData(uid : String) {
         withContext(Dispatchers.IO){
            val clearOrders = async { orderRepository.clearOrders(uid) }
            val clearMessages = async { botRepository.clearMessages() }
            val clearUser = async { userRepository.clearUser() }

            return@withContext awaitAll(clearOrders,clearMessages,clearUser)
        }
    }

    fun setCartClearedFalse(){
        orderRepository.setCartClearedFalse()
    }


}