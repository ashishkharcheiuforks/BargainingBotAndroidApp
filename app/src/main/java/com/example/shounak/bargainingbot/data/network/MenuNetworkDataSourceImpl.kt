package com.example.shounak.bargainingbot.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shounak.bargainingbot.data.db.entity.Drinks
import com.example.shounak.bargainingbot.data.db.entity.Food
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class MenuNetworkDataSourceImpl : MenuNetworkDataSource {

    private val COST = "cost"
    private val DESCRIPTION = "description"

    private val drinksDocRef = FirebaseFirestore.getInstance().collection("Drinks").document("Drinks")
    private val foodDocRef = FirebaseFirestore.getInstance().collection("Food").document("Food")

    private var _downloadedDrinksMenu = MutableLiveData<ArrayList<Drinks>>()
    override val downloadedDrinksMenu: LiveData<ArrayList<Drinks>>
        get() = _downloadedDrinksMenu

    private var _downloadedFoodMenu = MutableLiveData<ArrayList<Food>>()
    override val downloadedFoodMenu: LiveData<ArrayList<Food>>
        get() = _downloadedFoodMenu

    @Suppress("UNCHECKED_CAST")
    override fun getDrinksMenu() {

        drinksDocRef.addSnapshotListener(EventListener { snapshot, firebaseFirestoreException ->
            val drinksArray: ArrayList<Drinks> = ArrayList(30)
            val dataMap: Map<String, Any> = snapshot?.data!!

            for (drinkType: String in dataMap.keys) {
                val singleTypeDrinkMap: Map<String, Any> = dataMap[drinkType] as Map<String, Any>
                for (drinkName: String in singleTypeDrinkMap.keys) {
                    val drink = Drinks(drinkType, drinkName, singleTypeDrinkMap[drinkName].toString())
                    drinksArray.add(drink)
                }
            }

            _downloadedDrinksMenu.postValue(drinksArray)

        })

    }

    @Suppress("UNCHECKED_CAST")
    override fun getFoodMenu() {

        foodDocRef.addSnapshotListener(EventListener { snapshot, firebaseFirestoreException ->

            val foodArray : ArrayList<Food> = ArrayList(30)
            val dataMap : Map<String,Any> = snapshot?.data!!

            for (foodType : String in dataMap.keys){
                val singleTypeFoodMap : Map<String, Any> = dataMap[foodType] as Map<String, Any>
                for (foodName : String in singleTypeFoodMap.keys){
                    val singleFoodData = singleTypeFoodMap[foodName] as Map<String,Any>
                    val food = Food(foodType,foodName,singleFoodData[COST].toString(),singleFoodData[DESCRIPTION].toString())
                    foodArray.add(food)
                }
            }

            _downloadedFoodMenu.postValue(foodArray)

        })

    }
}