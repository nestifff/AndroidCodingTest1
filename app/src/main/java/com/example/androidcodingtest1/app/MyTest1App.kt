package com.example.androidcodingtest1.app

import android.app.Application
import com.example.androidcodingtest1.app.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyTest1App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyTest1App)
            modules(uiModule)
        }
    }
}
