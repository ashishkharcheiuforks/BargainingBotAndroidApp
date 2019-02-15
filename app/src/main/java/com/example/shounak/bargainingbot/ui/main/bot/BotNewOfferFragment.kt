package com.example.shounak.bargainingbot.ui.main.bot

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.provider.PreferenceProvider
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bot_fragment_new_offer_ui.*
import kotlinx.android.synthetic.main.bot_new_offer_cost_popup_window.view.*
import kotlinx.android.synthetic.main.bot_new_offer_quantity_popup_window.view.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by Shounak on 14-Feb-19
 */

class BotNewOfferFragment() : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: BotViewModelFactory by instance()

    private lateinit var viewModel: BotViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bot_fragment_new_offer_ui, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var quantity = 0
        var cost = 0

        parentFragment?.apply {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        }

        bot_fragment_quantity_button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@BotNewOfferFragment.context)

            val view = layoutInflater.inflate(R.layout.bot_new_offer_quantity_popup_window, null)
            view.bot_new_offer_number_picker.apply {
                minValue = 1
                maxValue = 99
            }


            alertDialog
                .setView(view)
                .setPositiveButton("SELECT", DialogInterface.OnClickListener { _, _ ->
                    quantity = view.bot_new_offer_number_picker.value
                    bot_fragment_quantity_button.text = quantity.toString()
                })
                .show()

        }

        bot_fragment_cost_button.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@BotNewOfferFragment.context)
            val view = layoutInflater.inflate(R.layout.bot_new_offer_cost_popup_window, null)

            alertDialog
                .setView(view)
                .setPositiveButton("SELECT", DialogInterface.OnClickListener { _, _ ->
                    cost = view.bot_new_offer_cost_edittext.text.toString().toInt()
                    val text = "$cost \u20B9"
                    bot_fragment_cost_button.text = text
                })
                .show()
        }

        bot_fragment_make_offer_button.setOnClickListener {
            val lastOrderdDrinkName = viewModel.lastOrderedDrinkName
            val lastOrderedDrinkCurrentCost = viewModel.lastOrderedDrinkCurrentCost
            val userId = PreferenceProvider.getPrefrences(this@BotNewOfferFragment.context!!)
                .getString(PreferenceProvider.USER_ID, "")

            if (lastOrderdDrinkName != "" && lastOrderedDrinkCurrentCost != 0 && !userId.isNullOrBlank()
                && quantity != 0 && cost != 0
            ) {

                launch {
                    viewModel.sendAiDrinksOrderRequest(
                        name = lastOrderdDrinkName,
                        quantity = quantity,
                        offeredCost = cost,
                        currentCost = lastOrderedDrinkCurrentCost,
                        userId = userId
                    )

                }

            }
        }

    }
}