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
import kotlin.math.abs

class NestedVerticalScrollerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), NestedScrollingParent3, NestedScrollingChild3{
    companion object {
        const val TAG = "NestedVerticalScrollerView"
    }

    private var mParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private val mChildHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)
    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mIsBeingDragged = false

    private val mScroller = OverScroller(context)
    private var mVelocityTracker: VelocityTracker? = null

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

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if ((action == MotionEvent.ACTION_MOVE) && mIsBeingDragged) {
            return true
        }
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                val y = ev.y.toInt()
                val yDiff: Int = abs((y - mLastTouchY))
                // 注意点: 添加标志位判断(nestedScrollAxes and SCROLL_AXIS_VERTICAL) == 0
                //嵌套滑动无需拦截
                if (yDiff > mTouchSlop && (nestedScrollAxes and SCROLL_AXIS_VERTICAL) == 0) {
                    mIsBeingDragged = true
                    mLastTouchY = ev.y.toInt()
                    initVelocityTrackerIfNotExists()
                    mVelocityTracker?.addMovement(ev)
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
            }

            MotionEvent.ACTION_DOWN -> {
                initOrResetVelocityTracker()
                mVelocityTracker?.addMovement(ev)
                /*
             * If being flinged and user touches the screen, initiate drag;
             * otherwise don't. mScroller.isFinished should be false when
             * being flinged. We also want to catch the edge glow and start dragging
             * if one is being animated. We need to call computeScrollOffset() first so that
             * isFinished() is correct.
            */
                mScroller.computeScrollOffset()
                mIsBeingDragged = !mScroller.isFinished

                mLastTouchY = ev.y.toInt()
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                /* Release the drag */
                mIsBeingDragged = false
                recycleVelocityTracker()
                stopNestedScroll()
            }
        }

        /*
    * The only time we want to intercept motion events is if we are in the
    * drag mode.
    */
        return mIsBeingDragged
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN ->{
                initOrResetVelocityTracker()
                mVelocityTracker?.addMovement(event)

                mLastTouchY = event.y.toInt()
            }
            MotionEvent.ACTION_MOVE -> {

                initVelocityTrackerIfNotExists()
                mVelocityTracker?.addMovement(event)

                val y = event.y.toInt()
                // 获取滑动的距离 y 方向
                val dy = mLastTouchY - y
                val consumed = IntArray(2)
                scrollerSelf(dy,consumed)
                mLastTouchY = y - consumed[1]
            }

            MotionEvent.ACTION_UP ->{
                mVelocityTracker?.let {
                    it.computeCurrentVelocity(1000)
                    // 注意点: 取负值
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
            scrollerSelf(mScroller.currY - scrollY, IntArray(2))
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    // 注意点: 要重写此方法，不然获取的状态位是错误的
    override fun getNestedScrollAxes(): Int {
        return mParentHelper.nestedScrollAxes
    }

    fun scrollerSelf(dy: Int, consumed: IntArray) {
        if (dispatchNestedPreScroll(0, dy, consumed, null)) {
            Log.i(TAG, "dy: $dy, consumed: ${consumed[1]}")
            // 减去父 View 消耗的距离，如果还有剩余，子 View 消耗
            var remainingDy = dy - consumed[1]
            if (remainingDy != 0) {
                //子view自己消费
                scrollerInternal(remainingDy, consumed)
                // 分发给父view.nestedScroll
                dispatchNestedScroll(
                    0,
                    consumed[1],
                    0,
                    remainingDy - consumed[1],
                    null,
                    ViewCompat.TYPE_TOUCH
                )
            }
        } else {
            // 父 View 不能滑动了，子 View 自己消耗掉
            scrollerInternal(dy, IntArray(2))
        }
    }

    private fun scrollerInternal(dy: Int, consumed: IntArray) {
        val origin = scrollY
        val finalY = dy.coerceIn(-scrollY,mContentHeight  - height - scrollY )
        Log.i(TAG, "scrollBy: $dy")
        scrollBy(0, finalY)
        consumed[0] = 0
        consumed[1] = scrollY - origin
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


    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mChildHelper.startNestedScroll(axes, type)
    }

    override fun stopNestedScroll(type: Int) {
        return mChildHelper.stopNestedScroll(type)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mChildHelper.hasNestedScrollingParent(type)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        return mParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        return mParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyUnconsumed < 0){
            scrollerInternal(dyUnconsumed, consumed)
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type,IntArray(2))
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.i(TAG,"onNestedPreScroll dy $dy")
        if (dy > 0){
            scrollerInternal(dy, consumed)
        }
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        if (!consumed){
            dispatchNestedFling(0f, velocityY, true)
            flingInternal(velocityY.toInt())
            return true
        }
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }


}