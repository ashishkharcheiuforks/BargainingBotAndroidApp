package com.example.shounak.bargainingbot.ui.main.bot

import ai.api.AIConfiguration
import ai.api.android.AIDataService
import ai.api.model.AIRequest
import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.network.APIAIService
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.data.repository.BotRepository
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.*
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Bot View Model
 */

class BotViewModel(
    private val userRepository: UserRepository,
    private val apiaiService: APIAIService,
    private val botRepository: BotRepository
) : ViewModel() {


    private lateinit var aiDataService: AIDataService
    private lateinit var aiRequest: AIRequest
    private lateinit var navigatedFrom: NavigatedFrom
    private var isBundleChecked = false

    var foodAcknowledgement: String = ""

    val response = botRepository.botResponse
    val action = botRepository.botAction

    private val _userMessage = MutableLiveData<String>()
    val userMessage: LiveData<String>
        get() = _userMessage


    var lastOrderedDrinkName: String = ""
    var lastOrderedDrinkCurrentCost = 0

    private var _fragmentToReplaceWith = MutableLiveData<Fragment>()
    val fragmentToReplaceWith: LiveData<Fragment>
        get() = _fragmentToReplaceWith

    val messageHistory by lazyDeferred {
        botRepository.getSavedMessages()
    }


    fun setBundleDetails(navigatedFrom: NavigatedFrom) {
        this.navigatedFrom = navigatedFrom
    }


    suspend fun actionOnBundleCheck(args: BotFragmentArgs, context: Context) {
        if (!isBundleChecked) {
            when (navigatedFrom) {

                NavigatedFrom.DRINKS_MENU -> {
                    val uid = getUserId(context)
                    if (uid != null || uid != "Not Available") {
                        sendAiDrinksOrderRequest(
                            name = args.name,
                            quantity = args.quantity,
                            currentCost = args.currentCost,
                            offeredCost = args.offeredCost,
                            userId = uid!!
                        )

                        isBundleChecked = true

                    } else {
                        throw PrefrencesUserNullException()
                    }
                }

                NavigatedFrom.FOOD_MENU -> {
                    val foodOrderList = args.foodOrderList
                    if (foodOrderList != null) {
                        val foodOrderArrayList = Gson().fromJson<ArrayList<FoodCartOrder>>(foodOrderList)
                        val string = StringBuilder()
                        string.append("Great! Order placed for :\n")
                        for (item in foodOrderArrayList) {
                            string.append("${item.quantity} - ${item.name} \n")
                        }
                        string.append("Bon Appétit")

                        foodAcknowledgement = string.toString()
                    }

                    isBundleChecked = true

                }

                NavigatedFrom.ORDERS_FRAGMENT -> {
                    sendAiRequest("payment done")
                    isBundleChecked = true
                }

                NavigatedFrom.NONE -> {
                    isBundleChecked = true
                }
            }
        }

    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


    private fun getUserId(context: Context): String? {

        val pref = PreferenceProvider.getPrefrences(context)
        return pref.getString(PreferenceProvider.USER_ID, "Not Available")

    }

    fun setupApiAiService(context: Context) {
        val config = ai.api.android.AIConfiguration(
            APIToken.API_AI_CLIENT_ACCESS_TOKEN,
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
                lastOrderedDrinkName = name
                lastOrderedDrinkCurrentCost = currentCost

            }
        }

    }

    fun getPhotoUrl(): Uri? {
        val userPhotoUri = FirebaseAuth.getInstance().currentUser?.photoUrl
        return ProfileImageUrl.getSmallPhotoUrl(userPhotoUri)
    }

    suspend fun addFoodAcknowledgement(message: String) {
        withContext(Dispatchers.IO) {
            botRepository.addFoodAcknowledgement(message)
        }
    }

    fun replaceBottomFragmentWithCallback(fragment: Fragment) {
        _fragmentToReplaceWith.value = fragment
    }

    suspend fun getUserName(): String? {
        return userRepository.getCurrentUserLocal()?.firstName
    }

    suspend fun getIsRegular(): Boolean {
        return userRepository.getCurrentUserLocal()?.regular == true
    }
}
