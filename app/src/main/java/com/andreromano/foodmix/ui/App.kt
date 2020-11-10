package com.andreromano.foodmix.ui

import android.app.Application
import android.content.Context
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        @JvmStatic
        lateinit var context: Context
    }
}