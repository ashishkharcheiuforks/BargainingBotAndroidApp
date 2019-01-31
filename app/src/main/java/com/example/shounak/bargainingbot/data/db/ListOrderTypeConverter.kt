//package com.example.shounak.bargainingbot.data.db
//
//import androidx.room.TypeConverter
//import com.example.shounak.bargainingbot.data.db.entity.Order
//
//class ListOrderTypeConverter {
//
//    @TypeConverter
//    fun ListOrderToString(list: List<Order>): String {
//        var stringBuilder = StringBuilder()
//        list.forEach {
//            stringBuilder.append("${it.name}/${it.qty}/${it.cost}~/")
//        }
//        return stringBuilder.toString()
//    }
//
//    fun StringToListOrder(string: String): List<Order> {
//        lateinit var orderList: MutableList<Order>
//        val strSplit1: List<String> = string.split("~/")
//        val i: Int = 0
//        strSplit1.forEach {
//            var strSplit2 = it.split("/")
//            orderList[i] = Order(strSplit2[1], strSplit2[2], strSplit2[3])
//        }
//        return orderList as List<Order>
//    }
//}
//
//
//
