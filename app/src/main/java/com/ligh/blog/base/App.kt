package com.ligh.blog.base

import android.app.Application
import android.os.Looper
import com.ligh.block.source.handler.LooperMonitor

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {


    @Inject
    lateinit var looperMonitor : LooperMonitor

    override fun onCreate() {
        super.onCreate()

        Looper.getMainLooper().setMessageLogging(looperMonitor)
    }
}