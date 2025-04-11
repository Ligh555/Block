package com.ligh.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.ligh.chart.data.ChartEntry
import com.ligh.chart.drawable.AxisDrawable
import com.ligh.chart.drawable.GridDrawable
import com.ligh.chart.drawable.LineChartDrawable

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val drawables = mutableListOf<Drawable>()

    private var data: List<ChartEntry> = emptyList()



    fun setChartData(data: List<ChartEntry>) {
        this.data = data
        updateDrawables()
        invalidate()
    }

    private fun updateDrawables() {
        drawables.clear()

        if (data.isEmpty()) return

        val yLabelWidth = 60f
        val xLabelHeight = 40f
        val topMargin = 20f

        val chartRect = RectF(
            paddingLeft + yLabelWidth,
            paddingTop + topMargin,
            width - paddingRight.toFloat(),
            height - paddingBottom - xLabelHeight
        )


        val inflowMax = data.maxOf { it.inflow }
        val inflowMin = data.minOf { it.inflow }
        val indexMax = data.maxOf { it.index }
        val indexMin = data.minOf { it.index }

        val inflowPoints = mapToPoints(data, { it.inflow }, inflowMin, inflowMax, chartRect)
        val indexPoints = mapToPoints(data, { it.index }, indexMin, indexMax, chartRect)

        drawables += GridDrawable()
        drawables += AxisDrawable(data, inflowMin, inflowMax, indexMin, indexMax)
        drawables += LineChartDrawable(inflowPoints, Color.BLUE)
        drawables += LineChartDrawable(indexPoints, Color.RED)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateDrawables()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (drawable in drawables) {
            drawable.bounds = Rect(0, 0, width, height)
            drawable.draw(canvas)
        }


    }

    private fun mapToPoints(
        entries: List<ChartEntry>,
        valueSelector: (ChartEntry) -> Float,
        min: Float,
        max: Float,
        rect: RectF
    ): List<PointF> {
        val range = max - min
        val count = entries.size
        return entries.mapIndexed { index, entry ->
            val x = rect.left + index * (rect.width() / (count - 1).coerceAtLeast(1))
            val percent = if (range == 0f) 0.5f else (valueSelector(entry) - min) / range
            val y = rect.bottom - percent * rect.height()  // 注意：Y坐标是自上而下的
            PointF(x, y)
        }
    }

}
