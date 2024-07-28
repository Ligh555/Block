package com.ligh.blog.ipc.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import com.ligh.blog.ipc.ITestAidlInterface


class RemoteService : Service() {
    
    val subAidl = object : ITestAidlInterface.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
            
        }

        override fun test() {
            Log.d("ligh", "当前进程ID为："+Process.myPid() +"----"+Thread.currentThread().name+"----"+"test")
        }

    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("ligh", "当前进程ID为："+Process.myPid()+"----"+Thread.currentThread().name+"----"+"onBind")
      return  subAidl
    }
    

}