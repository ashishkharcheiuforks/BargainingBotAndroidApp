package com.example.shounak.bargainingbot.data.provider

import android.content.Context
import android.content.SharedPreferences

private val PREF_FILE = "com.example.shounak.bargainingbot.prefs"

object PreferenceProvider {

    val USER_ID = "user_id"

     fun getPrefrences(context : Context): SharedPreferences {

        return context.applicationContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }


}