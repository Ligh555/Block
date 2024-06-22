package com.ligh.blog

import android.app.Application
import android.os.Looper
import com.ligh.block.source.exception.CrashHandler
import com.ligh.block.source.handler.LooperMonitor

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {


    @Inject
    lateinit var looperMonitor : LooperMonitor

    override fun onCreate() {
        super.onCreate()
        initMessageLog()
    }

    private fun initMessageLog(){
        Looper.getMainLooper().setMessageLogging(looperMonitor)
        CrashHandler.init(this)
    }
}