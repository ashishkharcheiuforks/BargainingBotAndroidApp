package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.db.Dao.BotDao
import com.example.shounak.bargainingbot.data.db.entity.Message
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.internal.MessageFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Bot repository implementation
 */

class BotRepositoryImpl(
    private val apiaiService: APIAIService,
    private val botDao: BotDao
) : BotRepository {


    private val _botResponse = MutableLiveData<String>()
    override val botResponse: LiveData<String>
        get() = _botResponse

    private val _botAction = MutableLiveData<String>()
    override val botAction: LiveData<String>
        get() = _botAction


    override suspend fun sendAiRequest(messageToSend: String) {

        val res = apiaiService.sendRequest(messageToSend).await()
        _botAction.postValue(res.result.action)
        _botResponse.postValue(res.result.fulfillment.speech)
        withContext(Dispatchers.IO) {
            botDao.addMessage(
                Message(
                    Date().time,
                    res.result.fulfillment.speech,
                    MessageFrom.BOT
                )
            )
        }
    }

    override suspend fun saveUserMessage(message: String) {

        withContext(Dispatchers.IO) {
            botDao.addMessage(
                Message(
                    Date().time,
                    message,
                    MessageFrom.USER
                )
            )
        }

    }

    override suspend fun getSavedMessages(): LiveData<List<Message>> {
        return withContext(Dispatchers.IO) {
            botDao.getMessages()
        }
    }

    override suspend fun addFoodAcknowledgement(message: String) {
        botDao.addMessage(
            Message(
                Date().time,
                message,
                MessageFrom.BOT
            )
        )
    }

    override suspend fun clearMessages() {
        withContext(Dispatchers.IO) {
            botDao.clearMessages()
        }
    }
}