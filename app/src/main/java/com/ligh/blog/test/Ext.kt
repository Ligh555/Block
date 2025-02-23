package com.ligh.blog.test

import android.view.MotionEvent

fun MotionEvent.getActionMaskName() = when (actionMasked) {
    MotionEvent.ACTION_DOWN -> "ACTION_DOWN"
    MotionEvent.ACTION_MOVE -> "ACTION_MOVE"
    MotionEvent.ACTION_UP -> "ACTION_UP"
    MotionEvent.ACTION_CANCEL -> "ACTION_CANCEL"
    MotionEvent.ACTION_OUTSIDE -> "ACTION_OUTSIDE"
    else -> "else $actionMasked"
}