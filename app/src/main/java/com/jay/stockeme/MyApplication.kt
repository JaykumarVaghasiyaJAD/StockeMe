package com.jay.stockeme

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.jay.stockeme.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        startKoin{
            androidLogger()
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}