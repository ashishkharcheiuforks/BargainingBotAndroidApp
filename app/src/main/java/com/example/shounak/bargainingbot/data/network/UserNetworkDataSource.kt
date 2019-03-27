package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * User online database interface
 */
interface UserNetworkDataSource {

    val downloadedCurrentUser : LiveData<User>

    suspend fun getCurrentUser()


    suspend fun setCurrentUser(userId: String, data: User)


}