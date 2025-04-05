package com.ligh.block.source.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewConfigurationCompat
import com.ligh.block.source.actionName
import com.ligh.block.source.view.TAG
import kotlin.math.abs

class TestTouchViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var lastX = 0
    var lastY = 0

    val mSlop =  ViewConfiguration.get(context).scaledTouchSlop

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "TestTouchViewGroup dispatchTouchEvent: ${ev.actionName()} ")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "TestTouchViewGroup onInterceptTouchEvent: ${ev.actionName()}")
//        if (ev.action == MotionEvent.ACTION_MOVE){
//            val intercept = abs(ev.y - lastY ) > mSlop || abs(ev.x -lastX ) > mSlop
//            Log.i(TAG, "onInterceptTouchEvent: $intercept")
//            return intercept
//        }
        lastX = ev.x.toInt()
        lastY = ev.y.toInt()
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "TestTouchViewGroup onTouchEvent: ${event.actionName()} ")
        return super.onTouchEvent(event)
    }
}