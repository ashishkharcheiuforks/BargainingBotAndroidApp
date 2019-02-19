package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.Dao.UserDao
import com.example.shounak.bargainingbot.data.db.entity.User
import com.example.shounak.bargainingbot.data.network.UserNetworkDataSource
import com.example.shounak.bargainingbot.internal.ProfileImageUrl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private lateinit var id: String

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userNetworkDataSource: UserNetworkDataSource
) : UserRepository {


    init {
        userNetworkDataSource.downloadedCurrentUser.observeForever {
            if (it != null) {
                persistCurrentUser(it)
            } else {
                runBlocking { setCurrentUser() }
            }
        }
    }

    override suspend fun getCurrentUser(): LiveData<User> {

        return withContext(Dispatchers.IO) {
            initUser()  //TODO: Check if user data was written to firestore and user exists. If not add user again
            return@withContext userDao.getUser()
        }

    }


    override suspend fun getCurrentUserLocal(): User? {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getUserNonLive()
        }
    }


    private suspend fun setCurrentUser() {

        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {
            id = firebaseUser.uid

            val isRegular = false
            val nameArray = getNameArray(firebaseUser.displayName!!)
            val firstName = nameArray[0]
            val lastName = nameArray[1]

            val photoUrl = ProfileImageUrl.getLargePhotoUrl(firebaseUser.photoUrl)

            val user = User(id, firebaseUser.email!!, firstName, lastName, isRegular, photoUrl.toString())

            runBlocking {
                withContext(Dispatchers.IO) {
                    userNetworkDataSource.setCurrentUser(id, user)
                }
            }
        }
    }

    override suspend fun clearUser() {
        withContext(Dispatchers.IO) {
            userDao.clearUser()
        }
    }


    private suspend fun initUser() {
        userNetworkDataSource.getCurrentUser()
    }

    private fun getNameArray(name: String): List<String> {
        return name.split(" ")
    }


    private fun persistCurrentUser(user: User) {
        runBlocking {
            withContext(Dispatchers.IO) {
                userDao.setUser(
                    User(
                        uid = user.uid,
                        email = user.email,
                        firstName = user.firstName,
                        lastName = user.lastName,
                        regular = user.regular,
                        photoUrl = ProfileImageUrl.getLargePhotoUrl(FirebaseAuth.getInstance().currentUser?.photoUrl).toString()
                    )
                )
            }
        }
    }


}
