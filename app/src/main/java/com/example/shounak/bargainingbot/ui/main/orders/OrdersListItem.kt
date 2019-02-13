package com.example.shounak.bargainingbot.ui.main.orders

import com.example.shounak.bargainingbot.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.order_cart_list_item.*

/**
 * Created by Shounak on 13-Feb-19
 */
class OrdersListItem(
    private val name : String,
    private val quantity : Int,
    private val cost : Int
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            order_name.text = name
            order_quantity.text = quantity.toString()
            order_cost.text = cost.toString()
        }
    }

    override fun getLayout() = R.layout.order_cart_list_item
}