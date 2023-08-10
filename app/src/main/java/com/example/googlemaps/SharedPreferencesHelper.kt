package com.example.googlemaps

import android.content.Context

object SharedPreferencesHelper {

    private const val PREFS_NAME = "MyPrefs"
    private const val KEY_LAST_EMAIL = "lastEmail"
    private const val KEY_LAST_FRAGMENT = "lastFragment"

    fun saveLastEmail(context: Context, email: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_LAST_EMAIL, email)
        editor.apply()
    }

    fun getLastEmail(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LAST_EMAIL, "") ?: ""
    }

    fun saveLastFragment(context: Context, fragmentTag: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_LAST_FRAGMENT, fragmentTag)
        editor.apply()
    }

    fun getLastFragment(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LAST_FRAGMENT, "") ?: ""
    }
}
