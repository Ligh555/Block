package com.ligh.chart.drawable

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.Shader
import android.graphics.drawable.Drawable

class LineChartDrawable(
    private var points: List<PointF>,
    private val color: Int
) : Drawable() {

    private val linePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        style = Paint.Style.STROKE
        this.color = this@LineChartDrawable.color
    }

    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val dotPaint = Paint().apply {
        this.color = color
        style = Paint.Style.FILL
    }

    // 初始化 startColor 和 fillPaint.shader
    private val startColor = Color.argb(
        0x20,                     // Alpha 值（范围：0-255，此处 0x80=128）
        Color.red(color),         // 原始颜色的红色分量
        Color.green(color),       // 绿色分量
        Color.blue(color)         // 蓝色分量
    )

    init {
        fillPaint.shader = LinearGradient(
            0f, 0f,
            0f, 1f,
            startColor,              // 使用调整后的浅色
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
    }

    // 创建 Path 对象并在每次绘制时重置
    private val path = Path()
    // 添加标志变量来跟踪路径是否需要重新计算
    private var isPathDirty = true

    override fun draw(canvas: Canvas) {
        if (points.isEmpty()) return

        // 如果路径需要重新计算，则重新计算路径
        if (isPathDirty) {
            calculatePath()
            isPathDirty = false
        }

        // 1. 绘制渐变填充
        drawGradientFill(canvas)

        // 2. 绘制折线
        drawLinePath(canvas)

        // 3. 绘制数据点
        drawDataPoints(canvas)
    }

    // 新增方法用于计算路径
    private fun calculatePath() {
        path.reset() // 重置路径
        path.apply {
            // 构建与折线相同的路径
            moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val curr = points[i]
                val midX = (prev.x + curr.x) / 2
                cubicTo(
                    midX, prev.y,
                    midX, curr.y,
                    curr.x, curr.y
                )
            }
            // 闭合路径形成填充区域
            lineTo(points.last().x, bounds.bottom.toFloat())
            lineTo(points.first().x, bounds.bottom.toFloat())
            close()
        }
    }

    private fun drawGradientFill(canvas: Canvas) {
        // 更新 LinearGradient 的边界
        (fillPaint.shader as LinearGradient).setLocalMatrix(
            android.graphics.Matrix().apply {
                setTranslate(0f, bounds.top.toFloat())
                postScale(1f, bounds.height().toFloat())
            }
        )

        canvas.drawPath(path, fillPaint)
    }

    private fun drawLinePath(canvas: Canvas) {

        canvas.drawPath(path, linePaint)
    }

    private fun drawDataPoints(canvas: Canvas) {
        for (point in points) {
            canvas.drawCircle(point.x, point.y, 6f, dotPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        linePaint.alpha = alpha
        fillPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        linePaint.colorFilter = colorFilter
        fillPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    // 新增方法用于更新数据点
    fun updatePoints(newPoints: List<PointF>) {
        if (points != newPoints) {
            points = newPoints
            isPathDirty = true // 标记路径需要重新计算
            invalidateSelf() // 通知视图重新绘制
        }
    }
}