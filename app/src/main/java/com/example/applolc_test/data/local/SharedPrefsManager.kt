package com.example.applolc_test.data.local
import android.content.Context
import android.content.SharedPreferences

class SharedPrefsManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("CustomerPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "access_token"
        private const val KEY_TOKEN_EXPIRY = "token_expiry"
        private const val KEY_VISITED_PREFIX = "visited_"
    }

    fun saveToken(token: String, expiry: String) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_TOKEN_EXPIRY, expiry)
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun isCustomerVisited(customerId: Int): Boolean {
        return prefs.getBoolean("$KEY_VISITED_PREFIX$customerId", false)
    }

    fun markCustomerAsVisited(customerId: Int) {
        prefs.edit().putBoolean("$KEY_VISITED_PREFIX$customerId", true).apply()
    }

    fun clearVisitedStatus() {
        val editor = prefs.edit()
        prefs.all.keys.filter { it.startsWith(KEY_VISITED_PREFIX) }
            .forEach { editor.remove(it) }
        editor.apply()
    }
}