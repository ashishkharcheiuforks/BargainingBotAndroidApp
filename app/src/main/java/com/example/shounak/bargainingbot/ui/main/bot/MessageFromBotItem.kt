package com.example.shounak.bargainingbot.ui.main.bot

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

        Glide.with(viewHolder.containerView)
            .load(R.mipmap.ic_launcher)
            .apply(RequestOptions().circleCrop())
            .into(viewHolder.message_from_bot_image_view)

    }

    override fun getLayout() = R.layout.message_from_bot

}