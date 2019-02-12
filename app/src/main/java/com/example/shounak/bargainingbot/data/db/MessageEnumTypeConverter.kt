package com.example.shounak.bargainingbot.data.db

import androidx.room.TypeConverter
import com.example.shounak.bargainingbot.internal.MessageFrom

/**
 * Created by Shounak on 12-Feb-19
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