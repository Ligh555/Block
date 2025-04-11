package com.ligh.chart.drawable
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class GridDrawable : Drawable() {

    private val paint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 1f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val stepCount = 5

        val height = bounds.height() - 60f
        val width = bounds.width().toFloat()

        for (i in 0..stepCount) {
            val y = bounds.top + i * height / stepCount
            canvas.drawLine(0f, y, width, y, paint)
        }
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha }
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter }
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}