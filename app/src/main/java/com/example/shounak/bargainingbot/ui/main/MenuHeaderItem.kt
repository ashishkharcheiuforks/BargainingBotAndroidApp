package com.example.shounak.bargainingbot.ui.main

import com.example.shounak.bargainingbot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.menu_header_item.*

/**
 * Created by Shounak on 08-Feb-19
 */
class MenuHeaderItem(private val title: String) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.menu_header_title.text = title
    }

    override fun getLayout() = R.layout.menu_header_item
}
