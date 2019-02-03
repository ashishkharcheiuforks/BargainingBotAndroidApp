package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 31-Jan-19
 */
interface UserNetworkDataSource {

    val downloadedCurrentUser : LiveData<User>

    suspend fun getCurrentUser()


    suspend fun setCurrentUser(userId: String, data: User)


}