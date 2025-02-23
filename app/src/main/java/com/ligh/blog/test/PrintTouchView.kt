package com.ligh.blog.test

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class PrintTouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "PrintTouchView"
    }

    init {
        setOnClickListener {
            Log.i(TAG, "$TAG:onclick")
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        Log.i(TAG, "$TAG  onTouchEvent: ${event.getActionMaskName()}   $result")
        return result
    }
}