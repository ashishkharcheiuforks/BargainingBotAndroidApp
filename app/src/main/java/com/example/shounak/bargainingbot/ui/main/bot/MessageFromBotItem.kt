package com.example.shounak.bargainingbot.ui.main.bot

import com.example.shounak.bargainingbot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.message_from_bot.*

/**
 * Created by Shounak on 12-Feb-19
 */
class MessageFromBotItem(private val messageFromBot : String) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.message_from_bot_text_view.text = messageFromBot

    }

    override fun getLayout() = R.layout.message_from_bot

}