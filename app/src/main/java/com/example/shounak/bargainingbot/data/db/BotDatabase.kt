package com.example.shounak.bargainingbot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shounak.bargainingbot.data.db.Dao.MenuDao
import com.example.shounak.bargainingbot.data.db.Dao.UserDao
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 30-Jan-19
 */


@Database(entities = [User::class, Drinks::class, Food::class], version = 1)

abstract class BotDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun menuDao(): MenuDao

    companion object {
        @Volatile
        private var instance: BotDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BotDatabase::class.java, "AppDatabaseEntries.db")
                .build()

    }
}