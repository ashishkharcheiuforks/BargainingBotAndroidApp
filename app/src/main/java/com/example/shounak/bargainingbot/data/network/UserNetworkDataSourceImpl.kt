package com.example.shounak.bargainingbot.data.network

class UserNetworkDataSourceImpl(
    private val userFirestoreDatabase: UserFirestoreDatabase
) : UserNetworkDataSource {
    override suspend fun getCurrentUser(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addNewUser(userId: String , data: Any) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        userFirestoreDatabase.addNewUser(userId,data)
    }

}