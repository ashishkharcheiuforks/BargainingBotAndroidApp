package com.example.shounak.bargainingbot.ui.main.orders

import com.example.shounak.bargainingbot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

/**
 * Created by Shounak on 19-Feb-19
 */
class EmptyOrdersItem : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout() = R.layout.empty_orders_item

}