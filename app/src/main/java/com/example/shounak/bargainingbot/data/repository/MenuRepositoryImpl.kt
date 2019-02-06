package com.example.shounak.bargainingbot.data.repository

import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.Dao.MenuDao
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.example.shounak.bargainingbot.data.network.MenuNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MenuRepositoryImpl(
    private val menuNetworkDataSource: MenuNetworkDataSource,
    private val menuDao: MenuDao
) : MenuRepository {


    init {
        menuNetworkDataSource.downloadedDrinksMenu.observeForever {
            persistDrinksMenu(it)
        }

        menuNetworkDataSource.downloadedFoodMenu.observeForever {
            persistFoodMenu(it)
        }
    }


    override suspend fun getDrinksMenu(): LiveData<List<Drinks>> {
        return withContext(Dispatchers.IO) {
            initDrinksMenu()
            return@withContext menuDao.getDrinksMenu()
        }

    }

    override suspend fun getFoodMenu(): LiveData<List<Food>> {
        return withContext(Dispatchers.IO) {
            initFoodMenu()
            return@withContext menuDao.getFoodMenu()
        }
    }


    private fun initDrinksMenu() {
        if (menuDao.getDrinksMenu().value != null) {
            return
        } else {
            menuNetworkDataSource.getDrinksMenu()
        }
    }

    private fun initFoodMenu() {
        if (menuDao.getFoodMenu().value != null) {
            return
        } else {
            menuNetworkDataSource.getFoodMenu()
        }
    }


    private fun persistDrinksMenu(drinksArrayList: ArrayList<Drinks>) {
        runBlocking {
            withContext(Dispatchers.IO) {
                for (drink: Drinks in drinksArrayList) {
                    menuDao.upsertDrinksMenu(drink)
                }
            }
        }
    }

    private fun persistFoodMenu(foodArrayList: ArrayList<Food>) {

        runBlocking {
            withContext(Dispatchers.IO) {
                for (food: Food in foodArrayList) {
                    menuDao.upsertFoodMenu(food)
                }
            }
        }

    }

}


