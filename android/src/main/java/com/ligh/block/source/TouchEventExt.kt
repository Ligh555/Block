package com.ligh.block.source

import android.view.MotionEvent

fun MotionEvent.actionName() :String{
    return when(action){
        MotionEvent.ACTION_DOWN -> " ACTION_DOWN"
        MotionEvent.ACTION_MOVE -> " ACTION_MOVE"
        MotionEvent.ACTION_UP -> " ACTION_UP"
        MotionEvent.ACTION_CANCEL -> " ACTION_CANCEL"
        else -> action.toString()
    }
}