package com.example.shounak.bargainingbot.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.internal.NoConnectivityException
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserNetworkDataSourceImpl(
    private val userFirestoreDatabase: UserFirestoreDatabase
) : UserNetworkDataSource {

    private val TAG = "UserNetDataSourceImpl"

    var _downloadedCurrentUser: MutableLiveData<User> = MutableLiveData<User>()
    override val downloadedCurrentUser: LiveData<User>
        get() = _downloadedCurrentUser

//TODO: user is fetched when get user is called. Implementation for data change on firestore? Add SnapshotListener

    override suspend fun getCurrentUser() {

        withContext(Dispatchers.IO) {
            val result = userFirestoreDatabase.getCurrentUser()
                .addOnSuccessListener {
                    try {
                        if (it != null) {
                            val user = it.toObject(User::class.java)
                            _downloadedCurrentUser.postValue(user)

                        }

                    } catch (e: NoConnectivityException) {
                        Log.e(TAG, "No internet connection")
                    }
                }
            Tasks.await(result)

        }
    }

    override suspend fun setCurrentUser(userId: String, data: User) {
        userFirestoreDatabase.getCurrentUser().addOnSuccessListener {
            if (!it.exists()) {
                runBlocking { userFirestoreDatabase.addNewUser(userId, data) }
            } else {
                val docData = HashMap<String, Any>()
                docData["photoUrl"] = data.photoUrl

                runBlocking { userFirestoreDatabase.updateUser(userId, docData) }

            }
        }

    }


}


