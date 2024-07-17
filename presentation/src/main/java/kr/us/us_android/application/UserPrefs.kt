package kr.us.us_android.application

import android.content.Context
import android.content.SharedPreferences

object UserPrefs {
    private const val PREFS_NAME = "us_prefs"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_BIRTH_DATE = "user_birth_date"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var userEmail: String?
        get() = sharedPreferences.getString(KEY_USER_EMAIL, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_USER_EMAIL, value).apply()
        }

    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_USER_ID, value).apply()
        }

    var userName: String?
        get() = sharedPreferences.getString(KEY_USER_NAME, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_USER_NAME, value).apply()
        }

    var userBirthDate: String?
        get() = sharedPreferences.getString(KEY_USER_BIRTH_DATE, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_USER_BIRTH_DATE, value).apply()
        }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }
}
