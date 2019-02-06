package com.example.shounak.bargainingbot.ui.main.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
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
                drinks_menu_text_view.text = it.toString()
            })
        }
    }

}
