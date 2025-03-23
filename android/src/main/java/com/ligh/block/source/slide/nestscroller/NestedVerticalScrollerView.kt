package com.ligh.block.source.slide.nestscroller

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.ligh.base.ext.dp2px
import com.ligh.block.source.slide.nestscroller.NestedVerticalScrollerViewTest.Companion
import kotlin.math.abs

class NestedVerticalScrollerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs){
    companion object {
        const val TAG = "NestedVerticalScrollerView"
    }

    private val mScroller = OverScroller(context)
    private var mVelocityTracker: VelocityTracker? = null

    private var mLastTouchX = 0
    private var mLastTouchY = 0


    private var mContentHeight = 0

    init {
        orientation = VERTICAL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mContentHeight = 0
        for (child in children) {
            val lp = child.layoutParams
            when (lp.height) {
                // 注意点: 对于子view为WRAP_CONTENT，需要重新测量高度，
                ViewGroup.LayoutParams.WRAP_CONTENT -> {
                    measureChild(
                        child,
                        MeasureSpec.makeMeasureSpec(child.measuredWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.UNSPECIFIED),
                    )
                }
                ViewGroup.LayoutParams.MATCH_PARENT -> {

                }
            }
            mContentHeight += child.measuredHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN ->{
                initOrResetVelocityTracker()
                mVelocityTracker?.addMovement(event)

                mLastTouchX = event.x.toInt()
                mLastTouchY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {

                initVelocityTrackerIfNotExists()
                mVelocityTracker?.addMovement(event)

                val x = event.x.toInt()
                val y = event.y.toInt()
                // 获取滑动的距离 x 方向
                val dx = mLastTouchX - x
                // 获取滑动的距离 y 方向
                val dy = mLastTouchY - y
                mLastTouchX = x.toInt()
                mLastTouchY = y.toInt()
                scrollerInternal(dy, intArrayOf(2))
            }

            MotionEvent.ACTION_UP ->{
                mVelocityTracker?.let {
                    it.computeCurrentVelocity(1000)
                    flingInternal(-it.yVelocity.toInt())
                }
                recycleVelocityTracker()
            }
        }
        return true
    }




    override fun scrollTo(x: Int, y: Int) {
        // 注意点: 滑动边界处理
        // 垂直滑动边界
        val maxScrollY = mContentHeight - height
        val finalY = y.coerceIn(0, maxScrollY)
        Log.i(TAG, "scrollTo: finalY $finalY")
        super.scrollTo(x, finalY)
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            val currY = mScroller.currY
            scrollTo(0, currY)
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private fun scrollerInternal(dy: Int, consumed: IntArray) {
        val finalY = dy.coerceIn(-scrollY,mContentHeight  - height - scrollY )
        Log.i(TAG, "scrollBy: $dy")
        scrollBy(0, finalY)
    }

    private fun flingInternal(velocityY: Int) {
        val maxScrollY = (mContentHeight - height).coerceAtLeast(0)
        mScroller.fling(
            scrollX, scrollY,
            0, velocityY,
            0, 0,
            0, maxScrollY,
            0, 0
        )
        ViewCompat.postInvalidateOnAnimation(this)
    }


    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else {
            mVelocityTracker!!.clear()
        }
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker?.recycle()
            mVelocityTracker = null
        }
    }


}