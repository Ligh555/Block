package com.ligh.block.source.drawable

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 * 自定义圆角 Drawable，支持为每个角设置不同的圆角半径，并支持填充颜色和边框。
 *
 * @param cornerRadiusTopLeft 左上角的圆角半径
 * @param cornerRadiusTopRight 右上角的圆角半径
 * @param cornerRadiusBottomLeft 左下角的圆角半径
 * @param cornerRadiusBottomRight 右下角的圆角半径
 * @param fillColor 填充颜色
 * @param borderColor 边框颜色
 * @param borderWidth 边框宽度
 */
class RoundedCornerDrawable(
    private val cornerRadiusTopLeft: Float,
    private val cornerRadiusTopRight: Float,
    private val cornerRadiusBottomLeft: Float,
    private val cornerRadiusBottomRight: Float,
    private val fillColor: Int,
    private val borderColor: Int,
    private val borderWidth: Float
) : Drawable() {

    private val paintFill = Paint().apply {
        color = fillColor
        isAntiAlias = true // 启用抗锯齿
        style = Paint.Style.FILL
    }

    private val paintBorder = Paint().apply {
        color = borderColor
        isAntiAlias = true // 启用抗锯齿
        style = Paint.Style.STROKE
        strokeWidth = borderWidth
        strokeJoin = Paint.Join.ROUND  // 平滑路径连接
        strokeCap = Paint.Cap.ROUND    // 平滑路径端点
    }

    /**
     * 绘制圆角矩形。
     *
     * @param canvas 用于绘制的画布
     */
    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val path = Path()

        // 获取四个角的半径
        val rectF = RectF(bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat())

        // 绘制四个角分别设置圆角的矩形路径
        path.addRoundRect(
            rectF,
            floatArrayOf(
                cornerRadiusTopLeft, cornerRadiusTopLeft, // 左上角
                cornerRadiusTopRight, cornerRadiusTopRight, // 右上角
                cornerRadiusBottomLeft, cornerRadiusBottomLeft, // 左下角
                cornerRadiusBottomRight, cornerRadiusBottomRight // 右下角
            ),
            Path.Direction.CW
        )

        // 绘制填充区域
        canvas.drawPath(path, paintFill)

        // 绘制边框
        if (borderWidth > 0) {
            canvas.drawPath(path, paintBorder)
        }
    }

    /**
     * 设置透明度。
     *
     * @param alpha 透明度值（0-255）
     */
    override fun setAlpha(alpha: Int) {
        paintFill.alpha = alpha
        paintBorder.alpha = alpha
    }

    /**
     * 设置颜色过滤器。
     *
     * @param colorFilter 颜色过滤器
     */
    override fun setColorFilter(colorFilter: ColorFilter?) {
        paintFill.colorFilter = colorFilter
        paintBorder.colorFilter = colorFilter
    }

    /**
     * 获取 Drawable 的透明度,用域判断在绘制时是否还需要绘制下一层，如果是透明就还需要绘制下一层颜色
     *
     * @return 透明度模式，返回 PixelFormat.TRANSLUCENT 表示支持半透明
     */
    override fun getOpacity(): Int {
//        if (paintFill.xfermode == null) {
//            val alpha: Int = paintFill.alpha
//            if (alpha == 0) {
//                return PixelFormat.TRANSPARENT
//            }
//            if (alpha == 255) {
//                return PixelFormat.OPAQUE
//            }
//        }
        return PixelFormat.TRANSLUCENT
    }
}