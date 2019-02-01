package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 31-Jan-19
 */
interface UserNetworkDataSource {


    suspend fun getCurrentUser(userId: String)


    suspend fun setCurrentUser(userId: String, data: User)


}