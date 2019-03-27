package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentChange

/**
 * Order online database interface
 */
interface OrderNetworkDataSource {


    val drinksOrderChangeList: LiveData<List<DocumentChange>>
    suspend fun getDrinksOrders(userId: String)
    suspend fun addToPreviousOrders(data: HashMap<String, Any>)
    suspend fun clearOrders(userId : String)
}