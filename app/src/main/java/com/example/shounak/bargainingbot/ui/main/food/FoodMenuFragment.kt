package com.example.shounak.bargainingbot.ui.main.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.food_menu_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FoodMenuFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : FoodMenuViewModelFactory by instance()


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
                food_menu_text_view.text = it.toString()
            })
        }
    }

}
