package com.ligh.block.source.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.ligh.block.source.actionName
import com.ligh.block.source.view.TAG

class TestTouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        setOnClickListener {  }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "TestTouchView onTouchEvent: ${event.actionName()} ")
        return super.onTouchEvent(event)
    }
}