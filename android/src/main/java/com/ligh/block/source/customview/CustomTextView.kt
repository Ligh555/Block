package com.ligh.block.source.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.ligh.base.ext.dp2px
import com.ligh.base.ext.getBitMap
import com.ligh.block.source.R

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
    private val testText1 = "测试"
    private val testText2 =
        "我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的我是来测试的"


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textPaint.setColor(context.getColor(R.color.teal_200))
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), textPaint)


        textPaint.setColor(Color.BLACK)
        textPaint.textSize = 18f.dp2px()

        //1 固定文字，即测量后大小不会变化

        /*        //1）设置居中
                textPaint.textAlign = Paint.Align.CENTER //以 drawText 方法中传入的 x 坐标为中心进行绘制
                //2 计算垂直居中高度
                val bounds = Rect()
                textPaint.getTextBounds(testText1, 0, testText1.length, bounds)
                val y = bounds.run {
                    height / 2 - (top - bottom) / 2
                }
                canvas.drawText(testText1, width/ 2f, y.toFloat(), textPaint)*/

        //2 动态文字，以字体descent ，ascent 为基准
        /*        //1）设置居中
                textPaint.textAlign = Paint.Align.CENTER //以 drawText 方法中传入的 x 坐标为中心进行绘制
                //2）获取字体边界
                val fontMMetrics = Paint.FontMetrics()
                textPaint.getFontMetrics(fontMMetrics)
                //3) 计算垂直居中高度
        //        ascent：从基线到字体最高点的距离（负值）。
        //        descent：从基线到字体最低点的距离（正值）。
        //        top：从基线到字体理论最高点的距离（负值，通常比ascent更极端）。
        //        bottom：从基线到字体理论最低点的距离（正值，通常比descent更极端）。
                val y = fontMMetrics.run {
                    height / 2 - (descent + ascent) / 2
                }
                canvas.drawText(testText1, width / 2f, y, textPaint)*/

        //3 多行文字
        //1） 简单多行 public StaticLayout(
        //    CharSequence source, // 要绘制的文本
        //    TextPaint paint,     // 文本的画笔
        //    int width,           // 布局的宽度（用于换行）
        //    Layout.Alignment align, // 文本对齐方式
        //    float spacingmult,   // 行间距倍数
        //    float spacingadd,    // 行间距增加值
        //    boolean includepad,  // 是否包括额外的 padding
        //    TextUtils.TruncateAt ellipsize, // 文本截断方式
        //    int ellipsizedWidth  // 文本截断的宽度
        //)

        /*        val staticLayout = StaticLayout(
                    testText2,
                    0,
                    testText2.length,
                    textPaint,
                    width,
                    Layout.Alignment.ALIGN_NORMAL,
                    1f,
                    0f,
                    false,
                )
                staticLayout.draw(canvas)*/

        //2) 字节测量手动绘制多行,复杂情况下使用

        // 先绘制一个图片占位置
        val imageSize = 50f.dp2px().toInt()
        val imageY = 30f.dp2px()
        val bitmap = context.getBitMap(R.drawable.test_screen, imageSize)
        canvas.drawBitmap(bitmap, width - imageSize + 0f, imageY, textPaint)

        val fontMetrics = textPaint.fontMetrics
        var start = 0
        var verticalOffset = -fontMetrics.top
        val measureWidth = floatArrayOf(0f)
        while (start < testText2.length) {
            val maxWidth =
                if (verticalOffset + fontMetrics.bottom < imageY || verticalOffset + fontMetrics.top > imageY + imageSize) {
                    width
                } else {
                    width - imageSize
                }
            //获取在maxWidth 宽度下一行可以绘制多少个字符
            val count = textPaint.breakText(
                testText2,
                start,
                testText2.length,
                true,
                maxWidth.toFloat(),
                measureWidth
            )
            //绘制文本
            canvas.drawText(testText2, start, start + count, 0f, verticalOffset, textPaint)
            //更新下一行开始位置
            start += count
            //更新下一行偏移量
            verticalOffset += textPaint.fontSpacing
        }

    }

}