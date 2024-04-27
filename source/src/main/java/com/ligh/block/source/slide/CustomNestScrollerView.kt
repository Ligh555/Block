package com.ligh.block.source.slide

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView

class CustomNestScrollerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {

    var targetView: View? = null
    val TAG = "ligh"

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        targetView?.let {
            if (this@CustomNestScrollerView.scrollY < 100) {
                val diff = 100 - it.scrollY
                if (dy < diff) {
                    scrollBy(0, dy)
                    consumed[1] = dy

                } else {
                    scrollBy(0, diff)
                    consumed[1] = diff
                }
            }
        }
        Log.i(TAG, "onNestedPreScroll: $type  ${consumed[0]} ${consumed[1]}")
    }

    // NestedScrollingParent3
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        Log.i(TAG, "$type onNestedScroll: $dyUnconsumed")
//        if (dyUnconsumed > 0){
//            return
//        }
        super.onNestedScroll(
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        super.onStopNestedScroll(target, type)
    }
    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
        Log.i(TAG, "scrollTo: x")
    }
}