package com.example.shounak.bargainingbot.data.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class OrderNetworkDataSourceImpl : OrderNetworkDataSource {

    private val _drinksOrderChangeList = MutableLiveData<List<DocumentChange>>()
    override val drinksOrderChangeList : LiveData<List<DocumentChange>>
        get() = _drinksOrderChangeList

    override suspend fun getDrinksOrders(context: Context) {

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Orders")
        val sharedPrefs = PreferenceProvider.getPrefrences(context)
        val userid = sharedPrefs.getString(PreferenceProvider.USER_ID,"Not Available")

        collectionRef
            .whereEqualTo("userid", userid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val data = querySnapshot?.documentChanges
                Log.d("firestore", data.toString())
                _drinksOrderChangeList.postValue(data)
            }


    }

}