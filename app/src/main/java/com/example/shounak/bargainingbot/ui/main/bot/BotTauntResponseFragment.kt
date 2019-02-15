package com.example.shounak.bargainingbot.ui.main.bot


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.bot_fragment_taunt_response.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class BotTauntResponseFragment : ScopedFragment(), KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : BotViewModelFactory by instance()

    private lateinit var viewModel : BotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bot_fragment_taunt_response, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        parentFragment?.apply {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(BotViewModel::class.java)
        }

        bot_fragment_taunt_new_offer_button.setOnClickListener {
           launch{ viewModel.sendAiRequest("New Offer") }
        }

        bot_fragment_taunt_accept_button.setOnClickListener {
            launch { viewModel.sendAiRequest("Accept") }
        }


    }

}
