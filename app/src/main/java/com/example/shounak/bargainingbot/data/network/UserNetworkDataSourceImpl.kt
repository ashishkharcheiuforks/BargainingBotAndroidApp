package com.example.shounak.bargainingbot.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.internal.NoConnectivityException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * User online database implementation
 */

class UserNetworkDataSourceImpl : UserNetworkDataSource {

    private val TAG = "UserNetDataSourceImpl"

    var _downloadedCurrentUser: MutableLiveData<User> = MutableLiveData<User>()
    override val downloadedCurrentUser: LiveData<User>
        get() = _downloadedCurrentUser

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users")

    override suspend fun getCurrentUser() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        withContext(Dispatchers.IO) {
            usersCollection.document(userId!!).addSnapshotListener { documentSnapshot, _ ->
                try {
                    if (documentSnapshot != null) {
                        val user = documentSnapshot.toObject(User::class.java)
                        _downloadedCurrentUser.postValue(user)

                    }

                } catch (e: NoConnectivityException) {
                    Log.e(TAG, "No internet connection")
                }
            }
        }
    }


    override suspend fun setCurrentUser(userId: String, data: User) {
        withContext(Dispatchers.IO) {
            usersCollection.document(userId).get().addOnSuccessListener {
                if (!it.exists()) {
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            usersCollection.document(userId).set(data)
                        }
                    }
                } else {
                    val docData = HashMap<String, Any>()
                    docData["photoUrl"] = data.photoUrl
                    docData["regular"] = data.regular

                    runBlocking {
                        withContext(Dispatchers.IO) {
                            usersCollection.document(userId).set(docData, SetOptions.merge())
                        }
                    }
                }
            }
        }
    }
}


