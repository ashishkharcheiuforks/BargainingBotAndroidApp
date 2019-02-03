package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.runBlocking

/**
 * Created by Shounak on 31-Jan-19
 */
class UserFirestoreDatabase {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    suspend fun getCurrentUser(): Task<DocumentSnapshot> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        return usersCollection.document(userId!!).get()

    }


    suspend fun addNewUser(userId: String, data: User) {

        runBlocking { usersCollection.document(userId).set(data) }
    }

    suspend fun updateUser(userId: String, data: Any) {

        runBlocking { usersCollection.document(userId).set(data, SetOptions.merge()) }
    }


}