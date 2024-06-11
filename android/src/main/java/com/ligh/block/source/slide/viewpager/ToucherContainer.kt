package com.ligh.block.source.slide.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout

class ToucherContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(ev)
    }
}