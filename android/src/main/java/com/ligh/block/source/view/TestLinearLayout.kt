package com.ligh.block.source.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

const val TAG = "ligh"

class TestLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.i(TAG, "onFinishInflate: ")
    }
}