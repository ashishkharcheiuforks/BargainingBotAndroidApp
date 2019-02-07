package com.example.shounak.bargainingbot.ui.main.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.drinks_menu_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DrinksMenuFragment : ScopedFragment() ,KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : DrinksMenuViewModelFactory by instance()

    private lateinit var viewModel: DrinksMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drinks_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(DrinksMenuViewModel::class.java)

        launch {
           val drinksMenu = viewModel.drinks.await()

            drinksMenu.observe(this@DrinksMenuFragment, Observer {
                if (!it.isEmpty()){
                    drinks_group_loading.visibility = View.GONE
                }

                initRecyclerView(it.toDrinksMenuItems())
            } )

//            })
        }
    }


    private fun List<Drinks>.toDrinksMenuItems() : List<DrinksMenuItem>{
        return this.map {
            DrinksMenuItem(it)
        }
    }

    private fun initRecyclerView(drinksList: List<DrinksMenuItem>) {

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(drinksList)

        }

        drinks_menu_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@DrinksMenuFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this@DrinksMenuFragment.context, "clicked", Toast.LENGTH_SHORT).show()
        }

    }

}
