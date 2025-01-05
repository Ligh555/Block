package com.ligh.base.ext

import android.content.res.Resources
import android.util.TypedValue

fun  Float.dp2px(value :Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Resources.getSystem().displayMetrics)