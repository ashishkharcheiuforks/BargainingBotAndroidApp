package com.example.shounak.bargainingbot.data.db

import androidx.room.TypeConverter

/**
 * Created by Shounak on 12-Feb-19
 */
class OrderEnumTypeConverter {

    @TypeConverter
    fun OrderEnumToString(e : OrderType): String {
        return e.name
    }

    @TypeConverter
    fun StringToOrderEnum(s : String): OrderType {
        return OrderType.valueOf(s)
    }
}