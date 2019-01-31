package com.example.shounak.bargainingbot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 30-Jan-19
 */


@Database(entities = [User::class], version = 1)

abstract class BotDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

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