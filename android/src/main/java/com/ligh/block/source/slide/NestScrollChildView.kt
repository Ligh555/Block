//package com.ligh.block.source.slide
//
//import androidx.core.view.NestedScrollingChild3
//import androidx.core.view.NestedScrollingChildHelper
//
//class NestScrollChildView :NestedScrollingChild3 {
//
//    private var mChildHelper: NestedScrollingChildHelper = NestedScrollingChildHelper(this)
//
//    /**
//     * 启用或禁用嵌套滚动的方法，设置为true，并且当前界面的View的层次结构是支持嵌套滚动的
//     * (也就是需要NestedScrollingParent嵌套NestedScrollingChild)，才会触发嵌套滚动。
//     * 一般这个方法内部都是直接代理给NestedScrollingChildHelper的同名方法即可
//     */
//    override fun setNestedScrollingEnabled(enabled: Boolean) {
//        mChildHelper.isNestedScrollingEnabled = enabled
//    }
//
//    /**
//     * 判断当前View是否支持嵌套滑动。一般也是直接代理给NestedScrollingChildHelper的同名方法即可
//     */
//    override fun isNestedScrollingEnabled(): Boolean {
//      return mChildHelper.isNestedScrollingEnabled
//    }
//
//    /**
//     * 表示 view 开始滚动了,一般是在 ACTION_DOWN 中调用，如果返回 true 则表示父布局支持嵌套滚动。
//     * 一般也是直接代理给 NestedScrollingChildHelper 的同名方法即可。这个时候正常情况会触发
//     *  Parent的 onStartNestedScroll() 方法
//     */
//    override fun startNestedScroll(axes: Int, type: Int): Boolean {
//       return mChildHelper.startNestedScroll(axes,type)
//    }
//
//    override fun startNestedScroll(axes: Int): Boolean {
//        return  mChildHelper.startNestedScroll(axes)
//    }
//
//
//    /**
//     * 一般是在事件结束比如 ACTION_UP 或者 ACTION_CANCLE 中调用,告诉父布局滚动结束。一般也是直
//     * 接代理给NestedScrollingChildHelper的同名方法即可
//     */
//    override fun stopNestedScroll(type: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun stopNestedScroll() {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * 判断当前View是否有嵌套滑动的Parent。一般也是直接代理给NestedScrollingChildHelper的
//     * 同名方法即可
//     */
//    override fun hasNestedScrollingParent(type: Int): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun hasNestedScrollingParent(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * 在当前View消费滚动距离之后。通过调用该方法，把剩下的滚动距离传给父布局。如果当前没有发生
//     * 嵌套滚动，或者不支持嵌套滚动，调用该方法也没啥用。
//     * 内部一般也是直接代理给NestedScrollingChildHelper的同名方法即可
//     * dxConsumed：被当前View消费了的水平方向滑动距离
//     * dyConsumed：被当前View消费了的垂直方向滑动距离
//     * dxUnconsumed：未被消费的水平滑动距离
//     * dyUnconsumed：未被消费的垂直滑动距离
//     * offsetInWindow：输出可选参数。如果不是null，该方法完成返回时，
//     * 会将该视图从该操作之前到该操作完成之后的本地视图坐标中的偏移量封装进该参数中，
//     * offsetInWindow[0]水平方向，offsetInWindow[1]垂直方向
//     * @return true：表示滚动事件分发成功,fasle: 分发失败
//     */
//    override fun dispatchNestedScroll(
//        dxConsumed: Int,
//        dyConsumed: Int,
//        dxUnconsumed: Int,
//        dyUnconsumed: Int,
//        offsetInWindow: IntArray?,
//        type: Int,
//        consumed: IntArray
//    ) {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun dispatchNestedScroll(
//        dxConsumed: Int,
//        dyConsumed: Int,
//        dxUnconsumed: Int,
//        dyUnconsumed: Int,
//        offsetInWindow: IntArray?,
//        type: Int
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun dispatchNestedScroll(
//        dxConsumed: Int,
//        dyConsumed: Int,
//        dxUnconsumed: Int,
//        dyUnconsumed: Int,
//        offsetInWindow: IntArray?
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * 在当前View消费滚动距离之前把滑动距离传给父布局。相当于把优先处理权交给Parent
//     * 内部一般也是直接代理给NestedScrollingChildHelper的同名方法即可。
//     * dx：当前水平方向滑动的距离
//     * dy：当前垂直方向滑动的距离
//     * consumed：输出参数，会将Parent消费掉的距离封装进该参数consumed[0]代表水平方向，
//     * consumed[1]代表垂直方向
//     * @return true：代表Parent消费了滚动距离
//     */
//
//    override fun dispatchNestedPreScroll(
//        dx: Int,
//        dy: Int,
//        consumed: IntArray?,
//        offsetInWindow: IntArray?,
//        type: Int
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun dispatchNestedPreScroll(
//        dx: Int,
//        dy: Int,
//        consumed: IntArray?,
//        offsetInWindow: IntArray?
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * 将惯性滑动的速度分发给Parent。内部一般也是直接代理给NestedScrollingChildHelper的同名
//     * 方法即可
//     * velocityX：表示水平滑动速度
//     * velocityY：垂直滑动速度
//     * consumed：true：表示当前View消费了滑动事件，否则传入false
//     * @return true：表示Parent处理了滑动事件
//     */
//    override fun dispatchNestedFling(
//        velocityX: Float,
//        velocityY: Float,
//        consumed: Boolean
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    /**
//     * NestedScrollingParent
//     * 在当前View自己处理惯性滑动前，先将滑动事件分发给Parent,一般来说如果想自己处理惯性的滑动
//     * 事件，
//     * 就不应该调用该方法给Parent处理。如果给了Parent并且返回true，那表示Parent已经处理了，
//     * 自己就不应该再做处理。
//     * 返回false，代表Parent没有处理，但是不代表Parent后面就不用处理了
//     * @return true：表示Parent处理了滑动事件
//     */
//    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
//        TODO("Not yet implemented")
//    }
//}