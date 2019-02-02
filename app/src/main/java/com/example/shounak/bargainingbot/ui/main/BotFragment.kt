package com.example.shounak.bargainingbot.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.shounak.bargainingbot.R

class BotFragment : Fragment() {

    companion object {
        fun newInstance() = BotFragment()
    }

    private lateinit var viewModel: BotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bot_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BotViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
