package com.example.shounak.bargainingbot.ui.main.bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.internal.PrefrencesUserNullException
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bot_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)

        viewModel.apply {
            setupApiAiService(this@BotFragment.context!!)

            response.observe(this@BotFragment, Observer {
                user_id_textview.text = it
            })
        }


        checkBundle()


        bot_send_button.setOnClickListener {
            launch {
                if(bot_fragment_edit_text.text.toString() != "") {
                    viewModel.sendAiRequest(bot_fragment_edit_text.text.toString())
                }
                bot_fragment_edit_text.text?.clear()
            }
        }
    }

    private fun checkBundle() {
        val args: BotFragmentArgs by navArgs()
        if (args.orderDrinks) {
            //TODO: send details to bot
            val uid = getUserId()
            launch {
                if (uid != null || uid!= "Not Available") {
                    viewModel.sendAiDrinksOrderRequest(name = args.name , quantity = args.quantity, currentCost = args.currentCost, offeredCost = args.offeredCost, userId = uid!! )
                }else{
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

}



