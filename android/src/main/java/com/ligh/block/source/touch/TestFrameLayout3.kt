package com.ligh.block.source.touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

class TestFrameLayout3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        setOnClickListener {
            Log.i("ligh", ": TestFrameLayout3")
        }
    }
}