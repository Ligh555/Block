package com.ligh.blog.test

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout

const val TAG = "PrintTouchLinearLayout"

class PrintTouchLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.i(TAG, "$TAG dispatchTouchEvent: ${ev.getActionMaskName()}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val result = super.onInterceptTouchEvent(ev)
        Log.i(TAG, "$TAG  onInterceptTouchEvent: ${ev.getActionMaskName()}   $result")
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "$TAG  onTouchEvent: ${event.getActionMaskName()}")
        return super.onTouchEvent(event)
    }
}