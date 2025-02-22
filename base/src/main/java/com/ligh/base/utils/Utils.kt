package com.ligh.base.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import androidx.annotation.DrawableRes

fun dp2px(value :Float):Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Resources.getSystem().displayMetrics)
}

fun getBitMap(context: Context,@DrawableRes id :Int,width :Int):Bitmap{
    val options = BitmapFactory.Options()
    //第一次读取只读边界获取宽高
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(context.resources,id,options)
    // 第二次读取目标尺寸的图片
    options.inJustDecodeBounds =false
    //原始宽度
    options.inDensity = options.outWidth
    //目标宽度
    options.outWidth = width
    return  BitmapFactory.decodeResource(context.resources,id,options)
}