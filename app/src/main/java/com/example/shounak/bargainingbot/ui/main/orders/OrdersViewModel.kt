package com.example.shounak.bargainingbot.ui.main.orders

import android.content.Context
import androidx.lifecycle.ViewModel;
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class OrdersViewModel(
    orderRepository: OrderRepository,
    context: Context
) : ViewModel() {

    val orders by lazyDeferred{
        orderRepository.getOrders(context)
    }

}
