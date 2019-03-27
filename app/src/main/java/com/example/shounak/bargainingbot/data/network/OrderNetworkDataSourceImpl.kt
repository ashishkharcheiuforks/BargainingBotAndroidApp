package com.example.shounak.bargainingbot.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Order online database implementation
 */

class OrderNetworkDataSourceImpl : OrderNetworkDataSource {


    private val db = FirebaseFirestore.getInstance()
    private val ordersCollectionRef = db.collection("Orders")
    private val prevOrdersCollectionRef = db.collection("PreviousOrders")


    private val _drinksOrderChangeList = MutableLiveData<List<DocumentChange>>()
    override val drinksOrderChangeList: LiveData<List<DocumentChange>>
        get() = _drinksOrderChangeList

    override suspend fun getDrinksOrders(userId: String) {

        ordersCollectionRef
            .whereEqualTo("userid", userId)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val data = querySnapshot?.documentChanges
                _drinksOrderChangeList.postValue(data)
            }


    }

    override suspend fun addToPreviousOrders(data: HashMap<String, Any>) {
        withContext(Dispatchers.IO) {
            prevOrdersCollectionRef.add(data)
        }
    }

    override suspend fun clearOrders(userId: String) {
        val querySnapshot = Tasks.await(
            ordersCollectionRef
                .whereEqualTo("userid", userId)
                .get()
        )

        val documents = querySnapshot.documents

        for (document in documents) {
            document.reference.delete()
                .addOnSuccessListener {
                    Log.d("clearOrders- Network", "deleted")
                }.addOnFailureListener {
                    Log.e("clearOrders - Network", it.toString())
                }
        }

    }


}