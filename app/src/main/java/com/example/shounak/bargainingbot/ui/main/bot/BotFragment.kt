package com.example.shounak.bargainingbot.ui.main.bot

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Message
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.internal.MessageFrom
import com.example.shounak.bargainingbot.internal.PrefrencesUserNullException
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.bot_fragment.*
import kotlinx.coroutines.launch
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigated = true
        return inflater.inflate(R.layout.bot_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)



        launch {
            viewModel.apply {
                setupApiAiService(this@BotFragment.context!!)

                viewModel.messageHistory.await().observe(this@BotFragment, Observer {
                    if (navigated) {
                        groupAdapter.clear()
                        addSavedMessages(it)
                        navigated = false
                    }

                })

                response.observe(this@BotFragment, Observer {
                    addBotMessage(it)
                })

                userMessage.observe(this@BotFragment, Observer {
                    addUserMessage(it)
                })
            }
        }

        initRecyclerView(this.context)

        checkBundle()

        setOnClickListener()

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
        if (args.orderDrinks) {
            //TODO: send details to bot
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
        } else if (args.orderFood) {
            //TODO: food order placed. Acknowledge
        }

    }

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
}



