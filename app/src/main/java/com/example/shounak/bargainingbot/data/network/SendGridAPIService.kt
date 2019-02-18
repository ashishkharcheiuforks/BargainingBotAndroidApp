package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 15-Feb-19
 */
interface SendGridAPIService {
    suspend fun sendEmail(data : HashMap<String,Any>, user : User)
}