package com.example.shounak.bargainingbot.ui.main.drinks

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.example.shounak.bargainingbot.ui.main.MenuHeaderItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.drinks_menu_fragment.*
import kotlinx.android.synthetic.main.drinks_menu_popup_window.view.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DrinksMenuFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: DrinksMenuViewModelFactory by instance()

    private lateinit var viewModel: DrinksMenuViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drinks_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DrinksMenuViewModel::class.java)


        launch {
            val drinksMenu = viewModel.drinks.await()

            drinksMenu.observe(this@DrinksMenuFragment, Observer {
                if (!it.isEmpty()) {
                    drinks_group_loading.visibility = View.GONE
                }


                initRecyclerView(it)

            })

        }

    }


    private fun initRecyclerView(drinksList: List<Drinks>) {

        launch {
            val drinksTitle = viewModel.getDrinksMenuTitles(drinksList)

            val groupAdapter: GroupAdapter<ViewHolder>

            groupAdapter = GroupAdapter<ViewHolder>().apply {
                for (drink in drinksTitle) {
                    val section = Section()
                    section.setHeader(MenuHeaderItem(drink))
                    section.addAll(viewModel.getDrinksListByType(drink).toDrinksMenuItems())
                    add(section)
                }

            }

            drinks_menu_recycler_view.apply {
                layoutManager = LinearLayoutManager(this@DrinksMenuFragment.context)
                adapter = groupAdapter
            }

            setOnClickListener(groupAdapter)
        }

    }


    private fun setOnClickListener(groupAdapter: GroupAdapter<ViewHolder>) {
        groupAdapter.setOnItemClickListener { item, view ->
            if (item.layout == R.layout.menu_header_item) {

            } else {
                Toast.makeText(this@DrinksMenuFragment.context, "clicked", Toast.LENGTH_SHORT).show()


                val builder = AlertDialog.Builder(this@DrinksMenuFragment.context)

                val inflater = activity!!.layoutInflater

                val popupView = inflater.inflate(R.layout.drinks_menu_popup_window, null)

                val alertDialog = builder.setView(popupView).setTitle((item as DrinksMenuItem).drinks.name).create()

                setupPopupView(popupView, item, alertDialog, view)

                alertDialog.show()

            }

        }

    }

    private fun setupPopupView(popupView: View, item: DrinksMenuItem, alertDialog: AlertDialog, mainView: View) {
        popupView.apply {


            number_picker.apply {
                minValue = 1
                maxValue = 50
            }
            val guaranteedPriceText = "Guaranteed price ${item.drinks.cost} \u20B9 each"
            guaranteed_price_textView.text = guaranteedPriceText
            pop_up_offer_button.setOnClickListener {

                var currentCost: Int = 0
                var name: String = ""
                var quantity: Int = 0
                var offeredCost: Int = 0

                if (pop_up_cost_edittext.text.isNullOrBlank()) {
                    Toast.makeText(this@DrinksMenuFragment.context, "Please enter a amount", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    quantity = number_picker.value
                    offeredCost = pop_up_cost_edittext.text.toString().toInt()
                    pop_up_cost_edittext.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    item.drinks.let {

                        currentCost = it.cost.toInt()
                        name = it.name

                    }
                }

                val toBotFragment = DrinksMenuFragmentDirections.actionToBotFragment(
                    quantity = quantity,
                    currentCost = currentCost,
                    offeredCost = offeredCost,
                    name = name,
                    orderDrinks = true
                )

                alertDialog.dismiss()

                Navigation.findNavController(mainView).navigate(toBotFragment)


            }
        }
    }


    private fun List<Drinks>.toDrinksMenuItems(): List<DrinksMenuItem> {
        return this.map {
            DrinksMenuItem(it)
        }
    }
}
