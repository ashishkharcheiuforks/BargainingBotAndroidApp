package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.Message

/**
 * Bot repository interface
 */
interface BotRepository {

    val botResponse : LiveData<String>

    suspend fun sendAiRequest(messageToSend: String)
    suspend fun saveUserMessage(message: String)

    suspend fun getSavedMessages(): LiveData<List<Message>>
    suspend fun addFoodAcknowledgement(message: String)
    val botAction: LiveData<String>
    suspend fun clearMessages()
}