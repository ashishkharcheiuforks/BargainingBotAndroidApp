package com.example.shounak.bargainingbot.ui.main.bot

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.data.db.entity.Message
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.internal.MessageFrom
import com.example.shounak.bargainingbot.internal.PrefrencesUserNullException
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.bot_fragment.*
import kotlinx.android.synthetic.main.bot_fragment_chat_ui.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class BotFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: BotViewModelFactory by instance()

    private lateinit var viewModel: BotViewModel
    private lateinit var groupAdapter: GroupAdapter<ViewHolder>
    private lateinit var linearLayoutManager: LinearLayoutManager
//    private var savedMessages: List<Message>? = null

    private var navigated = false
    private var isBundleChecked = false
    private var foodAcknowledgement = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("BotFragment", "OnCreateView")
        navigated = true
//        bot_bottom_fragment.fragmentManager?.beginTransaction()?.attach(BotBottomFragment())?.commit()
        return inflater.inflate(R.layout.bot_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("BotFragment", "OnActivityCreated")
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)



        launch {
            viewModel.apply {
                setupApiAiService(this@BotFragment.context!!)

                response.observe(this@BotFragment, Observer {
                    addBotMessage(it)
                })

                userMessage.observe(this@BotFragment, Observer {
                    addUserMessage(it)
                })

                viewModel.messageHistory.await().observe(this@BotFragment, Observer {
                    if (navigated) {
                        Log.d("BotFragment", "navigated is true")
                        groupAdapter.clear()
                        addSavedMessages(it)
                        navigated = false
                        if (foodAcknowledgement != "") {
                            addFoodAck(foodAcknowledgement)
                        }
                    }

                })


            }
        }

        initRecyclerView(this.context)

        checkBundle()

        setOnClickListener()

    }

    private fun addFoodAck(foodAcknowledgement: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                groupAdapter.add(MessageFromBotItem(foodAcknowledgement))
                linearLayoutManager.scrollToPosition(groupAdapter.itemCount - 1)
                viewModel.addFoodAcknowledgement(foodAcknowledgement)
            }

        }
    }

    private fun addSavedMessages(savedMessages: List<Message>?) {
        if (savedMessages != null && !savedMessages.isEmpty() && navigated) {
            for (message in savedMessages) {
                Log.d("addSavedMessages", message.toString())
                if (message.from == MessageFrom.USER) {
                    addUserMessage(message.message)
                } else if (message.from == MessageFrom.BOT) {
                    addBotMessage(message.message)
                }
            }
        }
    }


    fun setOnClickListener() {
        bot_send_button.setOnClickListener {
            launch {
                if (bot_fragment_edit_text.text.toString() != "") {
                    viewModel.sendAiRequest(bot_fragment_edit_text.text.toString())
                }
                bot_fragment_edit_text.text?.clear()
            }
        }
    }

    private fun initRecyclerView(context: Context?) {
        groupAdapter = GroupAdapter<ViewHolder>()
        val recyclerView = bot_fragment_recycler_view

        recyclerView.apply {
            linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.stackFromEnd = true
//            linearLayoutManager.reverseLayout = true
            layoutManager = linearLayoutManager
            adapter = groupAdapter
        }


    }

    private fun checkBundle() {
        val args: BotFragmentArgs by navArgs()
        if (args.orderDrinks && !isBundleChecked) {
            isBundleChecked = true
            val uid = getUserId()
            launch {
                if (uid != null || uid != "Not Available") {
                    viewModel.sendAiDrinksOrderRequest(
                        name = args.name,
                        quantity = args.quantity,
                        currentCost = args.currentCost,
                        offeredCost = args.offeredCost,
                        userId = uid!!
                    )

                } else {
                    throw PrefrencesUserNullException()
                }
            }
        } else if (args.orderFood && !isBundleChecked) {
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


        }

    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


    private fun getUserId(): String? {

        val pref = PreferenceProvider.getPrefrences(this@BotFragment.context!!)
        return pref.getString(PreferenceProvider.USER_ID, "Not Available")

    }

    private fun addUserMessage(message: String?) {
        val url = viewModel.getPhotoUrl()
        groupAdapter.add(
            MessageFromUserItem(
                message,
                url,
                this@BotFragment.context!!
            )
        )
        linearLayoutManager.scrollToPosition(groupAdapter.itemCount - 1)
    }

    private fun addBotMessage(message: String?) {
        if (message != null && message != "") {
            groupAdapter.add(
                MessageFromBotItem(
                    message
                )
            )
            linearLayoutManager.scrollToPosition(groupAdapter.itemCount - 1)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BotFragment", "OnDestroyView")
        viewModel.apply {
            response.removeObservers(this@BotFragment)
            userMessage.removeObservers(this@BotFragment)
        }

    }


}



