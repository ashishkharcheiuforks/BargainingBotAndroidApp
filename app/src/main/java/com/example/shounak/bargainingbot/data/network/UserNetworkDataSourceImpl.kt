package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User
import kotlinx.coroutines.runBlocking

class UserNetworkDataSourceImpl(
    private val userFirestoreDatabase: UserFirestoreDatabase
) : UserNetworkDataSource {


    override suspend fun getCurrentUser(userId: String) {
//        TODO("not implemented") //To change bargainingbotody of created functions use File | Settings | File Templates.


    }

    override suspend fun setCurrentUser(userId: String, data: User) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        userFirestoreDatabase.getCurrentUser(userId).addOnSuccessListener {
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