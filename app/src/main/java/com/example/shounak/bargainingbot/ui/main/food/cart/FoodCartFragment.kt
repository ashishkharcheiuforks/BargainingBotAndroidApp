package com.example.shounak.bargainingbot.ui.main.food.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_cart_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FoodCartFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FoodCartViewModelFactory by instance()


    private lateinit var cartOrdersList: List<FoodCartOrder>
    private var totalCost = MutableLiveData<Int>()
    private lateinit var viewModel: FoodCartViewModel
    private var groupAdapterCount = MutableLiveData<Int>()
    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        totalCost.postValue(0)
        groupAdapterCount.postValue(0)
        return inflater.inflate(R.layout.food_cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FoodCartViewModel::class.java)

        launch {

            val cartItems = viewModel.cartItems.await()

            cartItems.observe(this@FoodCartFragment, Observer {
                cartOrdersList = it
                groupAdapter.clear()
                var total =0
                for (order in it) {
                    groupAdapter.add(
                        FoodCartListItem(
                            order.name,
                            order.quantity,
                            order.cost
                        )
                    )
                    total += order.cost
                }
                totalCost.postValue(total)
                groupAdapterCount.postValue(groupAdapter.itemCount)

            })


            initRecyclerView(this@FoodCartFragment.context)
            bindUI()


        }


    }

    private fun bindUI() {
        groupAdapterCount.observe(this@FoodCartFragment, Observer {
            val cartItemText = "You have $it items in your cart"
            cart_items_count_textView.text = cartItemText
        })

        totalCost.observe(this@FoodCartFragment, Observer {
            cart_total_amount_text_view.text = it.toString()
        })


        place_order_button.setOnClickListener {
            launch {
                viewModel.addCartToOrders(cartOrdersList)
            }
        }
    }

    private fun initRecyclerView(context: Context?) {
        food_cart_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }

}
