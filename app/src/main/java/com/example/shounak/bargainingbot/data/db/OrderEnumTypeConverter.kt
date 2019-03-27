package com.example.shounak.bargainingbot.data.db

import androidx.room.TypeConverter

/**
 * Type converter to help database use non primitive types.
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