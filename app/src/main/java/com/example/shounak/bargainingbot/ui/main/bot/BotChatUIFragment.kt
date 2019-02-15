package com.example.shounak.bargainingbot.ui.main.bot

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bot_fragment_chat_ui.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by Shounak on 14-Feb-19
 */
class BotChatUIFragment() : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: BotViewModelFactory by instance()

    private lateinit var viewModel: BotViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bot_fragment_chat_ui, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        parentFragment?.apply {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        }

        bot_send_button.setOnClickListener {
            val message = bot_fragment_edit_text.text.toString()
            if (!message.isBlank()) {
                launch { viewModel.sendAiRequest(message) }
                bot_fragment_edit_text.text?.clear()
            }

        }

        bot_send_button.setOnLongClickListener {

            viewModel.replaceBottomFragmentWithCallback(BotChatButtonUIFragment())
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            return@setOnLongClickListener true
        }

    }

}