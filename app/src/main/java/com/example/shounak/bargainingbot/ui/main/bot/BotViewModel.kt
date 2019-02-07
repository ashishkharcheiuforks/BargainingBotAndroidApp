package com.example.shounak.bargainingbot.ui.main.bot

import ai.api.AIConfiguration
import ai.api.android.AIDataService
import ai.api.model.AIRequest
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.APIAIToken

class BotViewModel(
    private val userRepository: UserRepository,
private val apiaiService : APIAIService
) : ViewModel(){

    private var _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private lateinit var aiDataService: AIDataService
    private lateinit var aiRequest: AIRequest

    fun setupApiAiService(context: Context) {
        val config = ai.api.android.AIConfiguration(
            APIAIToken.CLIENT_ACCESS_TOKEN,
            AIConfiguration.SupportedLanguages.English,
            ai.api.android.AIConfiguration.RecognitionEngine.System
        )
        aiDataService = AIDataService(context, config)
        aiRequest = AIRequest()

        apiaiService.setupService(aiDataService,aiRequest)
    }

    suspend fun sendAiRequest(messageToSend : String){
        val res = apiaiService.sendRequest(messageToSend).await()
        Log.d("viewmodelres", res.toString())
        _response.postValue(res.result.fulfillment.speech)
    }



}
