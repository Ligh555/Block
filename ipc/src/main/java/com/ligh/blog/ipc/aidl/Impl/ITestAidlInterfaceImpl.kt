package com.ligh.blog.ipc.aidl.Impl

import android.os.Process
import android.util.Log
import com.ligh.blog.ipc.ITestAidlInterface

class ITestAidlInterfaceImpl : ITestAidlInterface.Stub() {
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
        Log.d("ligh", "当前进程ID为："+ Process.myPid() +"----"+Thread.currentThread().name+"----"+"test")
    }


}