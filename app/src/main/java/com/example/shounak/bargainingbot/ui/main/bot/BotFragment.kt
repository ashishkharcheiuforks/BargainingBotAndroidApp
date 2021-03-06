package com.example.shounak.bargainingbot.ui.main.bot

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Message
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.internal.DrawerLocker
import com.example.shounak.bargainingbot.internal.MessageFrom
import com.example.shounak.bargainingbot.internal.NavigatedFrom
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.example.shounak.bargainingbot.ui.main.MainActivityViewModel
import com.example.shounak.bargainingbot.ui.main.MainActivityViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.bot_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Bot Fragment
 * Main/home destination fragment for the app.
 *
 */

private const val WELCOME_USER_REG = "trigFirstWelcomeRegUser"
private const val WELCOME_USER = "trigFirstWelcome"

class BotFragment : ScopedFragment(), KodeinAware {


    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: BotViewModelFactory by instance()
    private val mainActivityViewModelFactory: MainActivityViewModelFactory by instance()

    private lateinit var viewModel: BotViewModel
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var groupAdapter: GroupAdapter<ViewHolder>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var navigatedFrom: NavigatedFrom

    private var navigated = false
    private var isYesNoFragDisplayed = false
    private var onCreateCalled = false
    private var isFragmentRestored = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateCalled = true
        isFragmentRestored = false
        checkBundleOnCreate()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigated = true
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val botChatButtonUIFragment = BotChatButtonUIFragment()
        fragmentTransaction.apply {
            replace(R.id.bot_bottom_fragment, botChatButtonUIFragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            commit()
        }
        return inflater.inflate(R.layout.bot_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        TODO("multiple backstack entries for botFragment")

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        activity.apply {
            mainActivityViewModel =
                ViewModelProviders.of(this!!, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
        }
        cancel_chip.visibility = View.GONE

        (activity as DrawerLocker).setDrawerEnabled()

        launch {
            viewModel.apply {
                setupApiAiService(this@BotFragment.context!!)

                response.observe(this@BotFragment, Observer {
                    if (!isFragmentRestored) {
                        addBotMessage(it)
                        isFragmentRestored = false
                    }
                })

                userMessage.observe(this@BotFragment, Observer {
                    if (shouldAddMessage(it)) {
                        addUserMessage(it)
                    }
                })

                action.observe(this@BotFragment, Observer {
                    manageUIWithAction(it)
                })

                fragmentToReplaceWith.observe(this@BotFragment, Observer {
                    if (it != null) {
                        replaceBottomFragmentWith(it)
                    }
                })

                viewModel.messageHistory.await().observe(this@BotFragment, Observer {
                    if (navigated) {
                        groupAdapter.clear()
                        addSavedMessages(it)
                        navigated = false
                    }
                    if (viewModel.foodAcknowledgement != "" && onCreateCalled) {
                        addFoodAck(foodAcknowledgement)
                        viewModel.foodAcknowledgement = ""
                    }

                })
            }

            mainActivityViewModel.apply {
                isTableSelected.observe(this@BotFragment, Observer {
                    val tableNumber = PreferenceProvider.getPrefrences(this@BotFragment.context!!)
                        .getInt(PreferenceProvider.TABLE_NUMBER, 0)
                    if (it and (tableNumber != 0)) {
                        addWelcomeMessage()
                    }

                })
            }
        }

        initRecyclerView(this.context)

        if (onCreateCalled) {
            actionOnBundleCheck()
        }


        cancel_chip.setOnClickListener {
            launch {
                viewModel.sendAiRequest("Cancel")
            }
            replaceBottomFragmentWith(BotChatButtonUIFragment())
            cancel_chip.visibility = View.GONE
        }

    }

    private fun addWelcomeMessage() {
        launch {
            viewModel.apply {
                if (groupAdapter.itemCount == 0) {
                    val name = getUserName()
                    if (getIsRegular()) {
                        if (!name.isNullOrBlank())
                            sendAiRequest("$WELCOME_USER_REG $name")
                        else
                            sendAiRequest(WELCOME_USER_REG)
                    } else {
                        if (!name.isNullOrBlank())
                            sendAiRequest("$WELCOME_USER $name")
                        else
                            sendAiRequest(WELCOME_USER)
                    }

                }
            }
        }
    }

    private fun manageUIWithAction(action: String?) {
        when (action) {
            "OrderDrinksCounter", "OrderDrinksAccept", "OrderDrinksOfferLow" -> {
                if (!isYesNoFragDisplayed) {
                    replaceBottomFragmentWith(BotYesNoFragment())
                    cancel_chip.visibility = View.VISIBLE
                    isYesNoFragDisplayed = true
                }

            }
            "OrderDrinksTaunt" -> {
                replaceBottomFragmentWith(BotTauntResponseFragment())
                cancel_chip.visibility = View.VISIBLE
                isYesNoFragDisplayed = false
            }

            "MakeNewOffer" -> {
                replaceBottomFragmentWith(BotNewOfferFragment())
                cancel_chip.visibility = View.VISIBLE
                isYesNoFragDisplayed = false
            }
            "PlaceDrinksOrder" -> {
                replaceBottomFragmentWith(BotChatButtonUIFragment())
                cancel_chip.visibility = View.GONE
                isYesNoFragDisplayed = false
            }
        }
    }

    private fun addFoodAck(foodAcknowledgement: String) {
        launch {
            viewModel.addFoodAcknowledgement(foodAcknowledgement)
        }
        groupAdapter.add(MessageFromBotItem(foodAcknowledgement))
        linearLayoutManager.scrollToPosition(groupAdapter.itemCount - 1)


    }

    private fun addSavedMessages(savedMessages: List<Message>?) {
        if (savedMessages != null && !savedMessages.isEmpty() && navigated) {
            for (message in savedMessages) {
                if (message.from == MessageFrom.USER) {
                    if (shouldAddMessage(message.message)) {
                        addUserMessage(message.message)
                    }
                } else if (message.from == MessageFrom.BOT) {
                    addBotMessage(message.message)
                }
            }
        }
    }


    private fun initRecyclerView(context: Context?) {
        groupAdapter = GroupAdapter<ViewHolder>()
        val recyclerView = bot_fragment_recycler_view

        recyclerView.apply {
            linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.stackFromEnd = true
            layoutManager = linearLayoutManager
            adapter = groupAdapter
        }


    }


    private fun checkBundleOnCreate() {
        val args: BotFragmentArgs by navArgs()


        navigatedFrom = when {
            args.orderDrinks -> {
                NavigatedFrom.DRINKS_MENU
            }
            args.orderFood -> {
                NavigatedFrom.FOOD_MENU
            }
            args.paymentDone -> {
                NavigatedFrom.ORDERS_FRAGMENT
            }
            else -> {
                NavigatedFrom.NONE
            }


        }
    }


    private fun actionOnBundleCheck() {

        viewModel.setBundleDetails(navigatedFrom)

        val args: BotFragmentArgs by navArgs()
        launch {
            viewModel.actionOnBundleCheck(args, this@BotFragment.context!!)
        }
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
        (activity as DrawerLocker).setDrawerDisabled()

        onCreateCalled = false
        viewModel.apply {
            response.removeObservers(this@BotFragment)
            userMessage.removeObservers(this@BotFragment)
        }

    }

    private fun replaceBottomFragmentWith(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.bot_bottom_fragment, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }

    private fun shouldAddMessage(message: String): Boolean {
        return !(
                message.contains("payment done", ignoreCase = true) or
                        message.contains("trigFirstWelcome", ignoreCase = true)
                )
    }
}


