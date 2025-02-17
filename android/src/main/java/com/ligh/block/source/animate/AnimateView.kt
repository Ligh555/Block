package com.ligh.block.source.animate

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.View

class AnimateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * 插帧动画
     */
    fun test() {
        // 使用ViewPropertyAnimator的例子
        this.animate().translationX(200f).setDuration(1000).start()
    }

    /**
     * 要让多个动画同时执行
     */
    fun testTogether() {
      /*  //方法1

        // 创建 PropertyValuesHolder
        val xAnimator = PropertyValuesHolder.ofFloat("x", 0f, 500f)   // 目标属性是 "x"（水平位置）
        val alphaAnimator = PropertyValuesHolder.ofFloat("alpha", 1f, 0f) // 目标属性是 "alpha"（透明度）

        // 使用 ObjectAnimator 来执行动画
        val animator = ObjectAnimator.ofPropertyValuesHolder(this, xAnimator, alphaAnimator)
        // 设置动画时长
        animator.duration = 1000L
        // 启动动画
        animator.start()*/

        val translationX = ObjectAnimator.ofFloat(this, "translationX", 0f, 500f)
        val alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)

        // 创建 AnimatorSet 并同时执行多个动画
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationX, alpha)

        // 设置动画时长
        animatorSet.duration = 1000L

        // 启动动画
        animatorSet.start()
    }

    /**
     * 按顺序执行多个动画
     */
    fun testSequentially(){
        val translationX = ObjectAnimator.ofFloat(this, "translationX", 0f, 500f)
        val alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)

        // 创建 AnimatorSet 并按顺序执行多个动画
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(translationX, alpha)

        // 设置动画时长
        animatorSet.duration = 1000L

        // 启动动画
        animatorSet.start()

    }


    /**
     * 关键帧显示关键节点
     * 演示如何使用 Keyframe 和 PropertyValuesHolder 创建动画，使视图的 x 坐标在不同时间点上有不同的值。
     *
     * 使用 Keyframe 可以定义一个属性在动画过程中在多个时间点的值，从而产生更复杂的动画效果。
     * 本例中，view 的 x 坐标在时间 0 时为 0，在时间 0.5 时为 300，在时间 1 时为 500。
     */
    fun testFrame() {
        // 创建多个 Keyframe 来定义不同时间点的值
        val keyframe1 = Keyframe.ofFloat(0f, 0f)    // t=0时，属性值为 0
        val keyframe2 = Keyframe.ofFloat(0.5f, 300f) // t=0.5时，属性值为 300
        val keyframe3 = Keyframe.ofFloat(1f, 500f)   // t=1时，属性值为 500

        // 使用 Keyframe 来创建 PropertyValuesHolder
        // PropertyValuesHolder 通过 Keyframe 让 "x" 属性在指定时间点上变化
        val xAnimator = PropertyValuesHolder.ofKeyframe("x", keyframe1, keyframe2, keyframe3)

        // 创建 ObjectAnimator
        // ObjectAnimator 会控制 "x" 属性的动画效果，改变视图的位置
        val animator = ObjectAnimator.ofPropertyValuesHolder(this, xAnimator)

        // 设置动画的持续时长
        animator.duration = 1000L  // 动画持续 1000 毫秒 (1 秒)

        // 启动动画
        animator.start() // 启动动画，动画将在视图上开始播放
    }

}