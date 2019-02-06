package com.example.shounak.bargainingbot

import android.app.Application
import com.example.shounak.bargainingbot.data.db.BotDatabase
import com.example.shounak.bargainingbot.data.network.*
import com.example.shounak.bargainingbot.data.repository.MenuRepository
import com.example.shounak.bargainingbot.data.repository.MenuRepositoryImpl
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.data.repository.UserRepositoryImpl
import com.example.shounak.bargainingbot.ui.login.LoginViewModelFactory
import com.example.shounak.bargainingbot.ui.main.MainActivityViewModelFactory
import com.example.shounak.bargainingbot.ui.main.bot.BotViewModelFactory
import com.example.shounak.bargainingbot.ui.main.drinks.DrinksMenuViewModelFactory
import com.example.shounak.bargainingbot.ui.main.food.FoodMenuViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by Shounak on 30-Jan-19
 */
class BotApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BotApplication))

        bind() from singleton { BotDatabase(instance()) }
        bind() from singleton { instance<BotDatabase>().userDao() }
        bind() from singleton { instance<BotDatabase>().menuDao() }
        bind() from singleton { UserFirestoreDatabase() }
        bind<MenuNetworkDataSource>() with singleton { MenuNetworkDataSourceImpl() }
        bind<UserNetworkDataSource>() with singleton { UserNetworkDataSourceImpl(instance()) }
        bind<MenuRepository>() with singleton { MenuRepositoryImpl(instance(), instance()) }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance(), instance()) }
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { MainActivityViewModelFactory(instance()) }
        bind() from provider { BotViewModelFactory(instance()) }
        bind() from provider { FoodMenuViewModelFactory(instance()) }
        bind() from provider { DrinksMenuViewModelFactory(instance()) }
    }


}