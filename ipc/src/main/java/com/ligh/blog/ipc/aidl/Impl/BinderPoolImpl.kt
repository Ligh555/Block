package com.ligh.blog.ipc.aidl.Impl

import android.os.IBinder
import com.ligh.blog.ipc.BinderPool.Companion.BINDER_TEST
import com.ligh.blog.ipc.IBinderPool

class BinderPoolImpl : IBinderPool.Stub() {
    override fun queryBinder(binderCode: Int): IBinder {
        return when (binderCode) {
            BINDER_TEST -> ITestAidlInterfaceImpl()
            else -> {
                throw Exception()
            }
        }
    }
}