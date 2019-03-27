package com.example.shounak.bargainingbot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shounak.bargainingbot.data.db.Dao.BotDao
import com.example.shounak.bargainingbot.data.db.Dao.MenuDao
import com.example.shounak.bargainingbot.data.db.Dao.OrderDao
import com.example.shounak.bargainingbot.data.db.Dao.UserDao
import com.example.shounak.bargainingbot.data.db.entity.*

/**
 * Local Sql database for the app
 */


@Database(
    entities = [User::class, Drinks::class, Food::class, Message::class, Order::class, FoodCartOrder::class],
    version = 1
)
@TypeConverters(MessageEnumTypeConverter::class, OrderEnumTypeConverter::class)
abstract class BotDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun menuDao(): MenuDao
    abstract fun botDao(): BotDao
    abstract fun orderDao(): OrderDao

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
                BotDatabase::class.java, "AppDatabaseEntries.db"
            )
                .build()

    }
}