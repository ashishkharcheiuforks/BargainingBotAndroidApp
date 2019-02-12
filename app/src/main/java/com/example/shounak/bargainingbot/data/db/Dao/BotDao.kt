package com.example.shounak.bargainingbot.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shounak.bargainingbot.data.db.entity.Message

/**
 * Created by Shounak on 12-Feb-19
 */

@Dao
interface BotDao {

    @Query("select * from Message order by timeMillis")
    fun getMessages() : LiveData<List<Message>>

    @Query("select * from Message where timeMillis = (select MAX(timeMillis) from Message)")
    fun getLatestMessage() : LiveData<Message>

    @Insert
    fun addMessage(message: Message)

}