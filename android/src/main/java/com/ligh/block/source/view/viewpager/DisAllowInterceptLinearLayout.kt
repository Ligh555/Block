package com.ligh.block.source.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children

interface IJustTouch {
    fun canVerticalScroller(): Boolean

    fun canHorizontalScroller(): Boolean
}

class DisAllowInterceptLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val result = super.dispatchTouchEvent(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                getJustTouchChild()?.let { child ->
                    if (child.canHorizontalScroller()) {
                        requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            MotionEvent.ACTION_MOVE ->{
                getJustTouchChild()?.let { child ->
                    if (!child.canHorizontalScroller()) {
                        requestDisallowInterceptTouchEvent(false)
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false)
            }
            else -> {}
        }

        return result
    }

    private fun getJustTouchChild(): IJustTouch? {
        for (child in children) {
            if (child is IJustTouch) {
                return child;
            }
        }
        return null
    }
}