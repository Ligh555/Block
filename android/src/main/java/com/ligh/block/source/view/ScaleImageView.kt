package com.ligh.block.source.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.OverScroller
import androidx.appcompat.widget.AppCompatImageView
import com.ligh.base.ext.dp2px
import com.ligh.base.utils.getBitMap
import com.ligh.block.source.R
import kotlin.math.max

/**
 * 自定义的 ImageView 控件，支持图片缩放和拖动操作
 *
 * 功能：
 * 1. 双击缩放：双击图片时进行缩放，来回切换大、小缩放状态
 * 2. 滑动拖动：手指滑动可以拖动图片，拖动范围由图片缩放比例决定
 * 3. 弹性滚动：快速滑动时，图片会在边界弹回
 */
val IMAGE_SIZE = 200F.dp2px().toInt() // 定义图片大小

class ScaleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    // 放大比例
    private val bigScale = 5f
    // 缩小比例
    private val smallScale = 1f

    private var offsetX = 0f
    private var offsetY = 0f

    private var translateX = 0f
    private var translateY = 0f

    // 当前的缩放比例
    var scaleFactory = smallScale
    // 加载的图片，使用懒加载，避免重复加载
    private val bitmap: Bitmap by lazy {
        getBitMap(context, R.drawable.test_screen, IMAGE_SIZE)
    }

    // 绘制图片的画笔，开启抗锯齿
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 边框的画笔
    private val boardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE // 设置为描边模式
        this.strokeWidth = 1.5f.dp2px()
    }

    // 用于实现弹性滚动效果
    private val overScroller = OverScroller(context)

    // 缩放动画
    private val animator = ObjectAnimator.ofFloat(smallScale, bigScale).apply {
        duration = 100
        addUpdateListener {
            scaleFactory = 1 + (it.animatedFraction * (bigScale - smallScale))
            Log.i("ScaleImageView", "scaleFactory: ${it.animatedFraction} $scaleFactory ")
            invalidate()
        }
    }

    // 手势识别器，用于处理双击、拖动等手势操作
    private val gestureDetector: GestureDetector = GestureDetector(context, object :
        GestureDetector.SimpleOnGestureListener() {

        /**
         * 双击事件：切换缩放状态
         */
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (animator.isRunning) {
                animator.cancel() // 如果动画正在进行，取消动画
            }
            if (scaleFactory == bigScale) {
                animator.reverse() // 如果当前是放大状态，则缩小
                translateX = 0f
                translateY = 0f
            } else {
                animator.start() // 否则执行放大动画
            }
            return true
        }

        /**
         * 滑动事件：拖动图片
         */
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (scaleFactory != smallScale) {
                translateX -= distanceX
                val maxX = (scaleFactory * bitmap.width - width) / 2
                val maxY = (scaleFactory * bitmap.height - height) / 2
                // 限制滑动范围
                translateX = translateX.coerceAtLeast(-maxX)
                translateX = translateX.coerceAtMost(maxX)
                translateY -= distanceY
                translateY = translateY.coerceAtLeast(-maxY)
                translateY = translateY.coerceAtMost(maxY)
                invalidate()
            }
            return true
        }

        /**
         * 弹性滑动：快速滑动后，滚动并弹回
         */
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val maxX = ((scaleFactory * bitmap.width - width) / 2).toInt()
            val maxY = ((scaleFactory * bitmap.height - height) / 2).toInt()
            overScroller.fling(
                translateX.toInt(),
                translateY.toInt(),
                velocityX.toInt(),
                velocityY.toInt(),
                -maxX,
                maxX,
                -maxY,
                maxY
            )
            refreshTranslate() // 更新滚动位置
            return true
        }
    })

    /**
     * 刷新滚动位置，更新图片位置
     */
    private fun refreshTranslate() {
        postOnAnimation {
            if (overScroller.computeScrollOffset()) {
                translateX = overScroller.currX.toFloat()
                translateY = overScroller.currY.toFloat()
                invalidate() // 刷新界面
                refreshTranslate() // 递归刷新滚动
            }
        }
    }

    /**
     * 控件尺寸变化时的回调，初始化图片的偏移量
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (w - bitmap.width) / 2f
        offsetY = (h - bitmap.height) / 2f
    }

    /**
     * 绘制控件的内容，绘制图片并支持缩放和拖动
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(translateX, translateY) // 应用平移
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), boardPaint) // 绘制边框
        // 在中点进行缩放
        canvas.scale(scaleFactory, scaleFactory, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint) // 绘制图片
    }

    /**
     * 处理触摸事件，将触摸事件交给手势识别器
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event) // 注意需要在此将点击事件委托出去
        return true
    }
}
