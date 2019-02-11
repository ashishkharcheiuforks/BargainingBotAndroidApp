package com.example.shounak.bargainingbot.ui.main.food

import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_food_menu.*

/**
 * Created by Shounak on 06-Feb-19
 */
class FoodMenuItem(
     val food :Food
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            food_name_text_view.text = food.name
            food_price_text_view.text = food.cost
            food_description_text_view.text = food.description
        }
    }

    override fun getLayout() = R.layout.item_food_menu
}