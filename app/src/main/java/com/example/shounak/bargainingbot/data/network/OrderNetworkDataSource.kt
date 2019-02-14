package com.example.shounak.bargainingbot.data.network

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.DocumentChange

/**
 * Created by Shounak on 13-Feb-19
 */
interface OrderNetworkDataSource {


    val drinksOrderChangeList: LiveData<List<DocumentChange>>
    suspend fun getDrinksOrders(context: Context)
}