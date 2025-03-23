package com.ligh.block.source.slide.nestscroller.example

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ligh.block.source.slide.nestscroller.strick.IStick

class TestStick @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs),IStick {
    override fun isStickTop(): Boolean {
        return true
    }

    override fun isStickBottom(): Boolean {
        return false
    }
}