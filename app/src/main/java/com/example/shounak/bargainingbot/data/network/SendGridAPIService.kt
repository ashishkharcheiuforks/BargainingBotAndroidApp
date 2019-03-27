package com.example.shounak.bargainingbot.data.network

import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * SendGrid Email API interface
 */
interface SendGridAPIService {
    suspend fun sendEmail(data : HashMap<String,Any>, user : User)
}