package com.ligh.base.frame


import android.os.Build
import android.os.Looper
import android.view.Choreographer
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 结合 Lifecycle 的帧同步助手
 * @param lifecycle 关联的生命周期对象（如 Activity/Fragment 的 lifecycle）
 * @param onUpdate 实际的 UI 更新逻辑（需自行处理空安全）
 */
class FrameSyncHelper(
    private val lifecycle: Lifecycle
) : DefaultLifecycleObserver {

    private val callbacks = mutableListOf<Runnable>()

    private val choreographer = Choreographer.getInstance()

    init {
        // 注册生命周期观察者
        lifecycle.addObserver(this)
    }
    // 帧回调
    private val frameCallback = Choreographer.FrameCallback { frameTimeNanos ->
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            requestUpdate()
        }
    }

    private fun doUpdate(){
        val currTime = System.currentTimeMillis()
        callbacks.forEach{
            it.run()
        }
        // 注意点: 要清除
        callbacks.clear()
        // 注意点: 防绿化通知
        if ((System.currentTimeMillis() - currTime ) / 1000 > 16){
            //showDialog
        }
    }


    // 触发 UI 更新请求
    private fun requestUpdate() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            doUpdate()
            choreographer.postFrameCallback(frameCallback)
        }
    }

    fun registerUpdate(runnable: Runnable){
        callbacks.add(runnable)
    }



    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        // 注意点: 内存泄漏
        choreographer.removeFrameCallback(frameCallback)
        callbacks.clear()
        lifecycle.removeObserver(this)
    }
}