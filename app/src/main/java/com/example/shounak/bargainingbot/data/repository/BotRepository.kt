package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.Message

/**
 * Created by Shounak on 12-Feb-19
 */
interface BotRepository {

    val botResponse : LiveData<String>

    suspend fun sendAiRequest(messageToSend: String)
    suspend fun saveUserMessage(message: String)

    suspend fun getSavedMessages(): LiveData<List<Message>>
}