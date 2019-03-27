package com.example.shounak.bargainingbot.data.db

import androidx.room.TypeConverter
import com.example.shounak.bargainingbot.internal.MessageFrom

/**
 * Type converter to help database use non primitive types.
 */

class MessageEnumTypeConverter {

    @TypeConverter
    fun MessageEnumToString(e : MessageFrom): String {
        return e.name
    }

    @TypeConverter
    fun StringToMessageEnum(s : String): MessageFrom {
        return MessageFrom.valueOf(s)
    }
}