package com.example.shounak.bargainingbot.ui.main.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.example.shounak.bargainingbot.ui.main.MenuHeaderItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_menu_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FoodMenuFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FoodMenuViewModelFactory by instance()


    private lateinit var viewModel: FoodMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.food_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FoodMenuViewModel::class.java)

        launch {
            val foodMenu = viewModel.food.await()

            foodMenu.observe(this@FoodMenuFragment, Observer {
                if (!it.isEmpty()) {
                    food_group_loading.visibility = View.GONE
                }
                initRecyclerView(it)
            })
        }
    }

    private fun List<Food>.toFoodMenuItem(): List<FoodMenuItem> {
        return this.map {
            FoodMenuItem(it)
        }
    }

    private fun initRecyclerView(foodList: List<Food>) {

        launch {
            val foodTitle = viewModel.getFoodMenuTitles(foodList)

            val groupAdapter = GroupAdapter<ViewHolder>().apply {
                for (food in foodTitle){
                    val section = Section()
                    section.setHeader(MenuHeaderItem(food))
                    section.addAll( viewModel.getFoodListByType(food).toFoodMenuItem())
                    add(section)
                }
            }

            food_menu_recycler_view.apply {
                layoutManager = LinearLayoutManager(this@FoodMenuFragment.context)
                adapter = groupAdapter
            }
        }

    }

}
