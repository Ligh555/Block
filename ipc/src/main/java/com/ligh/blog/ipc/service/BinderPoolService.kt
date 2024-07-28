package com.ligh.blog.ipc.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ligh.blog.ipc.aidl.Impl.BinderPoolImpl

class BinderPoolService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return BinderPoolImpl()
    }
}