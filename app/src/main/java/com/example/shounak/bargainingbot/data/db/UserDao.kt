package com.example.shounak.bargainingbot.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shounak.bargainingbot.data.db.entity.CURRENT_USER_ID
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 30-Jan-19
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(user : User)

    @Query("select * from User where constId=$CURRENT_USER_ID")
    fun getUser() : LiveData<User>
}