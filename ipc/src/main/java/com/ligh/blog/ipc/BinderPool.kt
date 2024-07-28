package com.ligh.blog.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import com.ligh.blog.ipc.service.BinderPoolService
import java.util.concurrent.CountDownLatch

class BinderPool {

    companion object {
        const val BINDER_TEST = 0
        val instance: BinderPool by lazy {
            BinderPool()
        }

    }

    var mContext: Context? = null
    private var mBinderPool: IBinderPool? = null

    private val mConnectBinderPoolCountDownLatch: CountDownLatch by lazy {
        CountDownLatch(1)
    }

    //------启动service start

    private val mBinderPoolConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {}
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            mBinderPool?.asBinder()?.linkToDeath(object : IBinder.DeathRecipient {
                override fun binderDied() {
                    //断线时重新连接
                    mBinderPool!!.asBinder().unlinkToDeath(this, 0)
                    mBinderPool = null
                    connectBinderPoolService()
                }

            }, 0)
            mConnectBinderPoolCountDownLatch.countDown()
        }
    }

    @Synchronized
    private fun connectBinderPoolService() {
        val service = Intent(mContext, BinderPoolService::class.java)
        mContext!!.bindService(
            service, mBinderPoolConnection,
            Context.BIND_AUTO_CREATE
        )
        try {
            mConnectBinderPoolCountDownLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    //------启动service end

    fun queryBinder(binderCode: Int): IBinder? {
        var binder: IBinder? = null
        try {
            if (mBinderPool != null) {
                binder = mBinderPool!!.queryBinder(binderCode)
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        return binder
    }
}