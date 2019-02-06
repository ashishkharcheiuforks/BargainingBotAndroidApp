package com.example.shounak.bargainingbot.data.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.Dao.UserDao
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.data.network.UserNetworkDataSource
import com.example.shounak.bargainingbot.internal.ProfileImageUrl.getLargePhotoUrl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private lateinit var id: String

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {

    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    init {
        userNetworkDataSource.downloadedCurrentUser.observeForever {
            if (it != null) {
                persistCurrentUser(it)
            } else {
                createUserAgain() //TODO: Test to see if this isn't being called every time resulting in 2 write opertaions
            }
        }
    }

    override suspend fun getCurrentUser(): LiveData<User> {


        return withContext(Dispatchers.IO) {
            val user = userDao.getUser()
            val userval = user.value
            if (userval?.uid != null && userval.uid == FirebaseAuth.getInstance().currentUser?.uid) {
                return@withContext user
            } else {
                initUserExistsCheck()  //TODO: Check if user data was written to firestore and user exists. If not add user again
                return@withContext userDao.getUser()
            }

        }

    }


    override suspend fun setCurrentUser(uid: String, name: String, email: String, photoUrl: Uri?) {
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
        }
    }


    private suspend fun initUserExistsCheck() {
        if (userDao.getUser().value != null) {
            return
        } else {
            userNetworkDataSource.getCurrentUser()
        }
    }

    private fun getNameArray(name: String): List<String> {
        return name.split(" ")
    }


    private fun persistCurrentUser(user: User) {
        runBlocking { withContext(Dispatchers.IO) { userDao.setUser(user) } }
    }

    private fun createUserAgain() {

        Log.d("UserRepositoryImpl","createUserAgain called")
        runBlocking {
            initUserExistsCheck()
            if (userDao.getUser() != null) {
                return@runBlocking
            } else {
                val url = getLargePhotoUrl(firebaseUser?.photoUrl)
                setCurrentUser(
                    firebaseUser!!.uid,
                    firebaseUser.displayName!!,
                    firebaseUser.email!!,
                    url
                )
            }
        }

    }

}
