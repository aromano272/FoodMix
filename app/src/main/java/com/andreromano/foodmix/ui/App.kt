package com.andreromano.foodmix.ui

import android.app.Application
import android.content.Context
import coil.Coil
import com.andreromano.foodmix.ui.utils.FoodMixImageLoaderFactory
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        JodaTimeAndroid.init(this)
        Timber.plant(Timber.DebugTree())
        Coil.setImageLoader(FoodMixImageLoaderFactory(applicationContext))
    }

    companion object {
        @JvmStatic
        lateinit var context: Context
    }
}