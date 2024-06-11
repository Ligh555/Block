package com.ligh.block.source.slide.nestscroller

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper

class NestScrollParentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), NestedScrollingParent3 {

    private var mParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)

    /**
     * 当NestedScrollingChild调用方法startNestedScroll()时,会调用该方法。主要就是通过返
     * 回值告诉系统是否需要对后续的滚动进行处理
     * child：该ViewParent的包含 NestedScrollingChild 的直接子 View，如果只有一层嵌套，和
     * target是同一个View
     * target：本次嵌套滚动的NestedScrollingChild
     * nestedScrollAxes：滚动方向
     * @return
     * true:表示我需要进行处理，后续的滚动会触发相应的回到
     * false: 我不需要处理，后面也就不会进行相应的回调了
     */
    // child 和 target 的区别，如果是嵌套两层如:Parent包含一个LinearLayout，LinearLayout里
    // 面才是 NestedScrollingChild 类型的View。这个时候，
    // child 指向 LinearLayout，target 指向 NestedScrollingChild；如果 Parent 直接就包含了
    // NestedScrollingChild，
    // 这个时候 target 和 child 都指向 NestedScrollingChild
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return  true
    }

    /**
     * 如果onStartNestedScroll()方法返回的是true的话,那么紧接着就会调用该方法.它是让嵌套滚
     * 动在开始滚动之前,
     * 让布局容器(viewGroup)或者它的父类执行一些配置的初始化的
     */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    /**
     * 停止滚动了,当子view调用stopNestedScroll()时会调用该方法
     */
    override fun onStopNestedScroll(target: View, type: Int) {

    }

    /**
     * 当子view调用dispatchNestedScroll()方法时,会调用该方法。也就是开始分发处理嵌套滑动了
     * dxConsumed：已经被target消费掉的水平方向的滑动距离
     * dyConsumed：已经被target消费掉的垂直方向的滑动距离
     * dxUnconsumed：未被tagert消费掉的水平方向的滑动距离
     * dyUnconsumed：未被tagert消费掉的垂直方向的滑动距离
     * type，手指是否触摸
     * consumed，消费的距离
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

    }

    /**
     * 当子view调用dispatchNestedPreScroll()方法是,会调用该方法。也就是在
     * NestedScrollingChild在处理滑动之前，
     * 会先将机会给Parent处理。如果Parent想先消费部分滚动距离，将消费的距离放入consumed
     * dx：水平滑动距离
     * dy：处置滑动距离
     * consumed：表示Parent要消费的滚动距离,consumed[0]和consumed[1]分别表示父布局在x和y
     * 方向上消费的距离.
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }
}