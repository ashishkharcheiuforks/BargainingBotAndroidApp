package com.example.shounak.bargainingbot.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.UserDao
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.data.network.UserNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private lateinit var id : String
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

        id = uid
        val isRegular = false
        val nameArray = getNameArray(name)
        val firstName = nameArray[0]
        val lastName = nameArray[1]

        val user = User(uid, email, firstName, lastName, isRegular, photoUrl.toString())

        runBlocking {
            launch {
                userNetworkDataSource.setCurrentUser(uid, user)
            }
            withContext(Dispatchers.IO){ userDao.setUser(user) } //Do this from main activity later
        }



    }

    override fun getUserForTest(): LiveData<User> {
        return userDao.getUser()

    }



    private fun getNameArray(name: String): List<String> {
        return name.split(" ")
    }

}