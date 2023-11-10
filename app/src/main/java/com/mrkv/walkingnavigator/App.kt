package com.mrkv.walkingnavigator

import android.app.Application
import com.yandex.mapkit.MapKitFactory

const val MAP_API_KEY = "61b0f699-0299-404a-aa4e-b643ee44f97d"

class App : Application() {



    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(MAP_API_KEY)
    }
}