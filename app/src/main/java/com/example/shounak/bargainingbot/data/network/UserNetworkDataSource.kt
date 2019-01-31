package com.example.shounak.bargainingbot.data.network

/**
 * Created by Shounak on 31-Jan-19
 */
interface UserNetworkDataSource {


    suspend fun getCurrentUser(userId : String)

    suspend fun addNewUser(userId : String, data : Any)

}