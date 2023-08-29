package com.example.adminapp

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
        //...
    }
}