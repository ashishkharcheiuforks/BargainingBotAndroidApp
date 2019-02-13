package com.example.shounak.bargainingbot.ui.main.orders

import androidx.lifecycle.ViewModel;
import com.example.shounak.bargainingbot.data.repository.OrderRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

class OrdersViewModel(
    orderRepository: OrderRepository
) : ViewModel() {

    val orders by lazyDeferred{
        orderRepository.getOrders()
    }

}
