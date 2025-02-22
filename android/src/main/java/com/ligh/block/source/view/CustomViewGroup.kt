package com.ligh.block.source.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

/**
 * 自定义 ViewGroup 类，用于实现类似于自动换行布局的效果。
 * 该类会根据子视图的宽度来决定每一行的布局，并在行满时自动换行，
 * 适用于需要在指定宽度内自动换行的场景。
 *
 * 主要功能：
 * 1. 在 `onMeasure` 方法中，测量每个子视图的宽高，并根据子视图的宽度判断是否需要换行。
 * 2. 在 `onLayout` 方法中，根据测量结果进行子视图的布局。
 * 3. 支持子视图的边距（margin）计算，确保子视图的布局正确。
 *
 * @constructor
 * 通过构造方法初始化该自定义 ViewGroup。支持常规的构造器传参。
 *
 * @param context 上下文对象
 * @param attrs 属性集
 * @param defStyleAttr 样式属性
 */
class CustomViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    // 存储每个子视图的布局位置和大小
    private val childRect = mutableListOf<Rect>()

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 确保 childRect 列表的大小足够存储所有子视图
        for (i in childRect.size until childCount) {
            childRect.add(Rect()) // 如果 childRect 不足，添加 Rect 对象
        }

        var widthUsed = paddingStart // 已使用的宽度
        var heightUsed = paddingTop // 已使用的高度
        var maxHeight = 0 // 当前行中最高的子视图高度
        var lineStartY = 0 // 当前行的起始 Y 坐标

        // 计算可用的宽度，减去左右 padding
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd
        val mode = MeasureSpec.getMode(widthMeasureSpec)

        // 确保宽度的测量模式不是 UNSPECIFIED，UNSPECIFIED 不支持这种布局
        if (mode == MeasureSpec.UNSPECIFIED) {
            throw Exception("该视图不支持 UNSPECIFIED 的宽度模式")
        }

        // 遍历所有子视图进行测量和布局
        for ((index, child) in children.withIndex()) {
            // 测量每个子视图，同时考虑其 margin
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                widthUsed,
                heightMeasureSpec,
                heightUsed
            )

            // 如果当前行放不下子视图，换到下一行
            if (widthUsed + child.measuredWidth + paddingEnd > width && widthUsed != 0) {
                widthUsed = paddingStart // 重置已使用的宽度
                lineStartY += maxHeight // 换行时，Y 坐标增加，移动到下一行
                heightUsed += maxHeight
                maxHeight = 0 // 重置当前行的最高高度
            }

            // 更新 childRect，记录当前子视图的布局位置和大小
            childRect[index].run {
                left = widthUsed
                top = heightUsed
                right = widthUsed + child.measuredWidth
                bottom = lineStartY + child.measuredHeight
            }

            // 更新当前行的最大高度
            maxHeight = maxHeight.coerceAtLeast(child.measuredHeight)

            // 更新已使用的宽度和高度
            widthUsed += child.measuredWidth
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 根据测量的结果，布局每个子视图
        for ((index, child) in children.withIndex()) {
            childRect[index].run {
                child.layout(left, top, right, bottom)
            }
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }
}
