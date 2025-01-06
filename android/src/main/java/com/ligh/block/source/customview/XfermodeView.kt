package com.ligh.block.source.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.ligh.base.ext.getBitMap
import com.ligh.block.source.R

/**
 * 图像融合——离屏缓冲blendMode or xfermode
 *
 * 目的：直接在canvas绘制 是在其背景上直接绘制， 离屏缓冲技术就可以 单独拿出一个空白的画布，在融合绘制好后放回去
 * 应用 ：绘制一些奇形怪状，或者像素融合的view
 */
class XfermodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private  val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)  //打开抗锯齿

    private val mBounds = RectF()
    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    init {
        //1、禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBounds.run {
            top = h / 4f
            bottom = h / 4f * 3
            left = w / 4f
            right = w / 4f * 3
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBounds.run {
            val width = right - left
            //2. 离屏缓冲，获取一块最小大小的屏幕
            val layerID = canvas.saveLayer(this,null)
            //3.绘制形状
            canvas.drawCircle(left + width /2, top + width / 2, width/2,mPaint )
            //4 设置融合模式
            mPaint.xfermode = mXfermode
            //5、融合目标元素
            canvas.drawBitmap(context.getBitMap(R.drawable.test_screen,width.toInt()), left,top,mPaint)
            //6、重置xfermode
            mPaint.xfermode = null
            //7、将 离屏缓冲恢复回来
            canvas.restoreToCount(layerID)
        }


    }



}