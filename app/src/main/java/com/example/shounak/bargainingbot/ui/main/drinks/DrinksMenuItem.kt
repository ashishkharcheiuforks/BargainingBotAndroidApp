package com.example.shounak.bargainingbot.ui.main.drinks

import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_drinks_menu.*

/**
 * Drinks menu item for recycler view
 */
class DrinksMenuItem(
    val drinks: Drinks
) : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.apply {
            drink_name_text_view.text  = drinks.name
            drink_price_text_view.text = drinks.cost
        }

    }

    override fun getLayout() = R.layout.item_drinks_menu


}