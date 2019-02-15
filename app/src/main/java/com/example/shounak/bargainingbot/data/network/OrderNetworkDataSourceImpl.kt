package com.example.shounak.bargainingbot.data.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderNetworkDataSourceImpl : OrderNetworkDataSource {


    private val db = FirebaseFirestore.getInstance()
    private val ordersCollectionRef = db.collection("Orders")
    private val prevOrdersCollectionRef = db.collection("PreviousOrders")


    private val _drinksOrderChangeList = MutableLiveData<List<DocumentChange>>()
    override val drinksOrderChangeList: LiveData<List<DocumentChange>>
        get() = _drinksOrderChangeList

    override suspend fun getDrinksOrders(context: Context) {

        val sharedPrefs = PreferenceProvider.getPrefrences(context)
        val userid = sharedPrefs.getString(PreferenceProvider.USER_ID, "Not Available")

        ordersCollectionRef
            .whereEqualTo("userid", userid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val data = querySnapshot?.documentChanges
                Log.d("firestore", data.toString())
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