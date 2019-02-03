package com.example.shounak.bargainingbot.data.provider

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Shounak on 02-Feb-19
 */
interface PreferenceProvider {

    fun getPrefrences(context : Context) : SharedPreferences

}