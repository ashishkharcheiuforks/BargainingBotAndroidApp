package com.example.shounak.bargainingbot.ui.main.food.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.shounak.bargainingbot.R

class FoodCartFragment : Fragment() {

    companion object {
        fun newInstance() = FoodCartFragment()
    }

    private lateinit var viewModel: FoodCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.food_cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodCartViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
