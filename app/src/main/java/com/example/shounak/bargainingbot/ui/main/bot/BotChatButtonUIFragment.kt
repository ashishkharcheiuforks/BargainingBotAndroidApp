package com.example.shounak.bargainingbot.ui.main.bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bot_fragment_chat_button_ui.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by Shounak on 14-Feb-19
 */
class BotChatButtonUIFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : BotViewModelFactory by instance()

    private lateinit var viewModel : BotViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bot_fragment_chat_button_ui, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        parentFragment?.apply {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        }

        to_chat_ui_button.setOnClickListener {

            viewModel.replaceBottomFragmentWithCallback(BotChatUIFragment())
        }

    }

}