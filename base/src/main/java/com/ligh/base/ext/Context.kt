package com.ligh.base.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes

fun Context.getBitMap( @DrawableRes id :Int, width :Int): Bitmap{
    val options = BitmapFactory.Options()
    //第一次读取只读边界获取宽高
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources,id,options)
    // 第二次读取目标尺寸的图片
    options.inJustDecodeBounds =false
    //原始宽度
    options.inDensity = options.outWidth
    //目标宽度
    options.outWidth = width
    return  BitmapFactory.decodeResource(resources,id,options)
}