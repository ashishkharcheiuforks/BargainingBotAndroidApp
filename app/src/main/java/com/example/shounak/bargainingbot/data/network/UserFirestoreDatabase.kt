package com.example.shounak.bargainingbot.data.network

import android.util.Log
import com.example.shounak.bargainingbot.data.db.entity.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/**
 * Created by Shounak on 31-Jan-19
 */
class UserFirestoreDatabase {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    suspend fun getCurrentUser(userId: String): Task<DocumentSnapshot> {

        return usersCollection.document("userId").get()
    }


    suspend fun addNewUser(userId: String, data: User) {

        usersCollection.document(userId).set(data)
            .addOnSuccessListener { Log.d("UserFirestoreDatabase", "OnSuccess called") }
    }

    suspend fun updateUser(userId: String, data: Any) {

        usersCollection.document(userId).set(data, SetOptions.merge())
    }


}