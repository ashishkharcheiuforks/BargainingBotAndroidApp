package com.example.shounak.bargainingbot.data.provider

import android.content.Context
import android.content.SharedPreferences

/**
 * A singleton class to provide SharedPreferences anywhere in the app
 */

private const val PREF_FILE = "com.example.shounak.bargainingbot.prefs"

object PreferenceProvider {

    const val USER_ID = "user_id"
    const val FIRST_LAUNCH = "first_launch"
    const val TABLE_NUMBER = "table_number"

     fun getPrefrences(context : Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
    }



}