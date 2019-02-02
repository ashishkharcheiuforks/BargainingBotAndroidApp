package com.example.shounak.bargainingbot.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.shounak.bargainingbot.R

class FoodMenuFragment : Fragment() {

    companion object {
        fun newInstance() = FoodMenuFragment()
    }

    private lateinit var viewModel: FoodMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.food_menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodMenuViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
