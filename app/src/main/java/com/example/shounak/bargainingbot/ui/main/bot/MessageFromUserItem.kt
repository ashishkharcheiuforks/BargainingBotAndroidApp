package com.example.shounak.bargainingbot.ui.main.bot

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shounak.bargainingbot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.message_from_user.*

/**
 * Created by Shounak on 12-Feb-19
 */
class MessageFromUserItem(private val messageFromUser : String?, private val profileImageUrl : Uri?, private val context: Context) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            message_from_user_text_view.text = messageFromUser

            Glide.with(context)
                .load(profileImageUrl)
                .apply(RequestOptions().circleCrop())
                .into(message_from_user_image_view)

        }

    }

    override fun getLayout() = R.layout.message_from_user
}