package com.example.shounak.bargainingbot.ui.main.bot

import ai.api.AIConfiguration
import ai.api.android.AIDataService
import ai.api.model.AIRequest
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.data.repository.BotRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.APIAIToken
import com.example.shounak.bargainingbot.internal.ProfileImageUrl
import com.example.shounak.bargainingbot.internal.lazyDeferred
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BotViewModel(
    private val userRepository: UserRepository,
    private val apiaiService: APIAIService,
    private val botRepository: BotRepository
) : ViewModel() {

//    private var _response = MutableLiveData<String>()
//    val response: LiveData<String>
//        get() = _response

    val messageHistory by lazyDeferred {
        botRepository.getSavedMessages()
    }


    val response = botRepository.botResponse
    private val _userMessage = MutableLiveData<String>()
    val userMessage: LiveData<String>
        get() = _userMessage

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

        apiaiService.setupService(aiDataService, aiRequest)
    }

    suspend fun sendAiRequest(messageToSend: String) {
        botRepository.saveUserMessage(messageToSend)
        _userMessage.postValue(messageToSend)
        botRepository.sendAiRequest(messageToSend)
    }

    suspend fun sendAiDrinksOrderRequest(
        name: String,
        quantity: Int,
        currentCost: Int,
        offeredCost: Int,
        userId: String
    ) {

        val messageForBot = StringBuilder()
        messageForBot.apply {
            append(quantity)
            append(" ")
            append(name)
            append(" for ")
            append(offeredCost)
            append(" rupees, current ")
            append(currentCost)
            append(" rupees uid ")
            append(userId)
        }
        val messageToSend = messageForBot.toString()
        withContext(Dispatchers.IO) {
            launch {
                botRepository.sendAiRequest(messageToSend)
            }
            launch {
                val messageToShow = "$quantity $name for $offeredCost \u20B9. What do you say?"
                botRepository.saveUserMessage(messageToShow)
                _userMessage.postValue(messageToShow)
            }
        }

    }

    fun getPhotoUrl(): Uri? {
        val userPhotoUri = FirebaseAuth.getInstance().currentUser?.photoUrl
        return ProfileImageUrl.getSmallPhotoUrl(userPhotoUri)
    }

    suspend fun addFoodAcknowledgement(message: String){
        botRepository.addFoodAcknowledgement(message)
    }

}
