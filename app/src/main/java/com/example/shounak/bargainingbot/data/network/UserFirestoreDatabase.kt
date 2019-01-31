package com.example.shounak.bargainingbot.data.network

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Shounak on 31-Jan-19
 */
class UserFirestoreDatabase {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    suspend fun getCurrentUser(userId: String): Task<DocumentSnapshot> {

        return usersCollection.document("userId").get()
    }

    suspend fun addNewUser(userId: String, data: Any) {
//        TODO("change Any() type for data with proper type")
        usersCollection.document(userId).set(data)
            .addOnSuccessListener { Log.d("UserFirestoreDatabase", "OnSuccess called") }
    }

}