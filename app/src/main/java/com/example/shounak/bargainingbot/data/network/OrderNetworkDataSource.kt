package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentChange

/**
 * Created by Shounak on 13-Feb-19
 */
interface OrderNetworkDataSource {


    val drinksOrderChangeList: LiveData<List<DocumentChange>>
    suspend fun getDrinksOrders(userId: String)
    suspend fun addToPreviousOrders(data: HashMap<String, Any>)
    suspend fun clearOrders(userId : String)
}