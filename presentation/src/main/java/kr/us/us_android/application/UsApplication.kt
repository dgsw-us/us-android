package kr.us.us_android.application

import android.app.Application
import android.content.Context

class UsApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager

        private lateinit var instance: UsApplication

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = PreferenceManager(getContext())
        UserPrefs.init(this)
    }
}
