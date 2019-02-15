package com.example.shounak.bargainingbot.ui.main.bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bot_fragment_yes_no_ui.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by Shounak on 14-Feb-19
 */
class BotYesNoFragment() : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    val viewModelFactory : BotViewModelFactory by instance()

    private lateinit var viewModel : BotViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bot_fragment_yes_no_ui, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        parentFragment?.apply {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        }

        bot_fragment_yes_button.setOnClickListener {
            launch {
                viewModel.sendAiRequest("yes")
            }
        }

        bot_fragment_no_button.setOnClickListener {
            launch {
                viewModel.sendAiRequest("no")
            }
        }

    }

}