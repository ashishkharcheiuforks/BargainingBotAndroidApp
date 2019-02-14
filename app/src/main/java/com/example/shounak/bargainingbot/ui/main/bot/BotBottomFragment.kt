package com.example.shounak.bargainingbot.ui.main.bot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment

/**
 * Created by Shounak on 14-Feb-19
 */
class BotBottomFragment : ScopedFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bot_fragment_chat_button_ui, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}