package com.ligh.chart.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import com.ligh.chart.data.ChartEntry

class AxisDrawable(
    private val entries: List<ChartEntry>,
    private val inflowMin: Float,
    private val inflowMax: Float,
    private val indexMin: Float,
    private val indexMax: Float
) : Drawable() {

    private val paint = Paint().apply {
        color = Color.GRAY
        textSize = 24f
        isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val chartHeight = bounds.height() - 60f
        val stepCount = 5

        for (i in 0..stepCount) {
            val y = bounds.top + i * chartHeight / stepCount
            val inflowValue = inflowMax - i * (inflowMax - inflowMin) / stepCount
            val indexValue = indexMax - i * (indexMax - indexMin) / stepCount

            canvas.drawText("%.0f".format(inflowValue), 0f, y, paint)
            canvas.drawText("%.0f".format(indexValue), bounds.right - 100f, y, paint)
        }

        val count = entries.size
        val spacing = bounds.width() / (count - 1).toFloat()
        val step = count / 4
        for (i in 0 until count step step) {
            val x = i * spacing
            canvas.drawText(entries[i].time, x, bounds.bottom.toFloat(), paint)
        }
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha }
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter }
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}