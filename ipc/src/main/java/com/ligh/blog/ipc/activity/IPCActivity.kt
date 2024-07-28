package com.ligh.blog.ipc.activity

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.blog.ipc.ITestAidlInterface
import com.ligh.blog.ipc.databinding.ActivityIpcactivityBinding
import com.ligh.blog.ipc.service.RemoteService

class IPCActivity : BaseActivity() {
    override val viewBinding: ActivityIpcactivityBinding by binding()

    override fun initViewBinding() {
        viewBinding.view.setOnClickListener {
            bind()
        }
    }

    private fun bind() {
        val service = Intent(this@IPCActivity, RemoteService::class.java)
        bindService(service, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val myAIDLInterface =
                    ITestAidlInterface.Stub.asInterface(service);//返回的是另一个进程Service中传入的MyAIDLInterface.Stub类的代理对象
                myAIDLInterface?.test()
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }

        }, Service.BIND_AUTO_CREATE)
    }

}