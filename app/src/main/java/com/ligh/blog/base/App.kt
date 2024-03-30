package com.ligh.blog.base

import android.app.Application

import dagger.hilt.android.HiltAndroidApp
@HiltAndroidApp
class App : Application() {


    override fun onCreate() {
        super.onCreate()
//        Looper.getMainLooper().setMessageLogging(looperMonitor)
    }
}