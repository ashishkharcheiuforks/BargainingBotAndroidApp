package com.example.shounak.bargainingbot.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.UserDao
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.data.network.UserNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {
    override suspend fun getCurrentUser(): LiveData<User> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUser()
        }

    }


    override suspend fun setCurrentUser(uid: String, name: String, email: String?, photoUrl: Uri?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val isRegular = getIsRegular()
        val user = User(uid, email, name, isRegular, photoUrl.toString())
        runBlocking {
            launch {
                userNetworkDataSource.addNewUser(uid, user)
            }.invokeOnCompletion { cause -> Log.d("UserRepositoryImpl", "invokeOnCompletion called : $cause") }
            withContext(Dispatchers.IO) { userDao.setUser(user) }
        }
    }


    private fun getIsRegular(): Boolean {
        return true
    }


}