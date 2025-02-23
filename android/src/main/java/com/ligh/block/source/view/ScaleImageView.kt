package com.ligh.block.source.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import com.ligh.base.ext.dp2px
import com.ligh.base.utils.getBitMap
import com.ligh.block.source.R

/**
 * 1、 如何设计图片尺寸
 *
 *
 */

val IMAGE_SIZE = 200F.dp2px().toInt()

class ScaleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val bigScale = 5f
    private val smallScale = 1f

    var scaleFactory = smallScale
    private val bitmap: Bitmap by lazy {
        getBitMap(context, R.drawable.test_screen, IMAGE_SIZE)
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val boardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE // 设置为描边模式
        this.strokeWidth = 1.5f.dp2px()
    }

    val animator = ObjectAnimator.ofFloat(smallScale,bigScale).apply {
        duration = 100
        addUpdateListener {
            scaleFactory = 1 +  (it.animatedFraction * (bigScale - smallScale) )
            Log.i("ScaleImageView", "scaleFactory: ${it.animatedFraction} $scaleFactory ")
            invalidate()
        }
    }


    private val gestureDetector: GestureDetector = GestureDetector(context, object :
        GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (animator.isRunning){
                animator.cancel()
            }
            if (scaleFactory == bigScale) {
                animator.reverse()
            } else {
                animator.start()
            }
            return true // 表示处理事件
        }
    })

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), boardPaint)
        canvas.scale(scaleFactory, scaleFactory, width / 2f, height / 2f) //注意要设置居中点,以居中点为中心开始放大
        canvas.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event) // 注意需要在此将点击事件委托出去
        return true
    }
}