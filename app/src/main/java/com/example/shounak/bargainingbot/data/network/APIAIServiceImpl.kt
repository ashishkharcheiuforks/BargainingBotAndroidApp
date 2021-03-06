package com.example.shounak.bargainingbot.data.network

import ai.api.android.AIDataService
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Dialogflow API service implementation
 */
class APIAIServiceImpl : APIAIService {

//    private var _response = MutableLiveData<String>()
//    override val response: LiveData<String>
//        get() = _response

    private lateinit var aiDataService: AIDataService
    private lateinit var aiRequest: AIRequest


    override fun setupService(aiDataService: AIDataService, aiRequest: AIRequest) {
        this.aiDataService = aiDataService
        this.aiRequest = aiRequest
    }


    override suspend fun sendRequest(messageToSend: String): Deferred<AIResponse> {
        return withContext(Dispatchers.IO) {
            async {
                aiRequest.setQuery(messageToSend)
                val result = aiDataService.request(aiRequest)
                result
            }
        }
    }

    override suspend fun sendDrinksOrderRequset(messageToSend: String): Deferred<AIResponse> {
        return withContext(Dispatchers.IO){
            async {
                aiRequest.setQuery(messageToSend)
                val result = aiDataService.request(aiRequest)
                result
            }
        }
    }


}


