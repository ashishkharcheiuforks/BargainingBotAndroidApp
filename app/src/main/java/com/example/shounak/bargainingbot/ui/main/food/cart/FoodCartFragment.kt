package com.example.shounak.bargainingbot.ui.main.food.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shounak.bargainingbot.R
import com.example.shounak.bargainingbot.data.db.entity.FoodCartOrder
import com.example.shounak.bargainingbot.ui.base.ScopedFragment
import com.example.shounak.bargainingbot.ui.main.food.FoodMenuFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemLongClickListener
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.food_cart_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class FoodCartFragment : ScopedFragment(), KodeinAware, OnItemLongClickListener {


    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FoodCartViewModelFactory by instance()


    private lateinit var cartList: ArrayList<FoodCartOrder>
    private var totalCost = MutableLiveData<Int>()
    private lateinit var viewModel: FoodCartViewModel
    private var groupAdapterCount = MutableLiveData<Int>()
    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    private var deletedItems: ArrayList<FoodCartListItem> = ArrayList(3)

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
                //                cartOrdersList = it
                groupAdapter.clear()
                var total = 0
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
            if (groupAdapter.itemCount != 0) {
                launch {
                    deleteItemsInListFromCart()
                    withContext(Dispatchers.IO) {
                        cartList = ArrayList<FoodCartOrder>(5)
                        val count = groupAdapter.itemCount
                        for (i in 0 until count) {
                            val item = groupAdapter.getItem(i) as FoodCartListItem
                            cartList.add(
                                FoodCartOrder(
                                    Date().time,
                                    item.name,
                                    item.quantity,
                                    item.cost
                                )
                            )
                        }
                        viewModel.addCartToOrders(cartList)
                    }
                    viewModel.clearFoodCart()

                    val toBotFragment = FoodMenuFragmentDirections.actionToBotFragment(
                        orderDrinks = false,
                        orderFood = true,
                        foodOrderList = Gson().toJson(cartList)
                    )
                    Navigation.findNavController(this@FoodCartFragment.view!!).navigate(toBotFragment)
                }


            }
        }

        groupAdapter.setOnItemLongClickListener(this@FoodCartFragment)
    }


    private fun initRecyclerView(context: Context?) {
        food_cart_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }

    override fun onItemLongClick(item: Item<*>, view: View): Boolean {


        val pos = groupAdapter.getAdapterPosition(item)
        groupAdapter.remove(item)
        deletedItems.add(item as FoodCartListItem)
        val newTotal = totalCost.value?.minus(item.cost)
        totalCost.postValue(newTotal)
        Snackbar.make(this@FoodCartFragment.view!!, "Item Removed", Snackbar.LENGTH_SHORT)
            .setAction("UNDO", View.OnClickListener {
                val restoredAmount = totalCost.value?.plus(item.cost)
                totalCost.postValue(restoredAmount)
                groupAdapter.add(pos, item)
                deletedItems.remove(item as FoodCartListItem)
            })
            .show()

        return true

    }


    private fun deleteItemsInListFromCart() {
        if (!deletedItems.isNullOrEmpty()) {
            for (item in deletedItems) {
                runBlocking {
                    viewModel.deleteItemFromFoodCart(item)
                }
            }
            deletedItems.clear()
        }
    }

    override fun onPause() {
        super.onPause()
        deleteItemsInListFromCart()
    }
}
