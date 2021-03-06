package com.example.shounak.bargainingbot.data.network

import ai.api.android.AIDataService
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import kotlinx.coroutines.Deferred

/**
 * Dialogflow API service interface
 */

interface APIAIService {

    suspend fun sendRequest(messageToSend: String): Deferred<out AIResponse>
    fun setupService(aiDataService: AIDataService, aiRequest: AIRequest)
    suspend fun sendDrinksOrderRequset(messageToSend: String): Deferred<AIResponse>
}
