package com.ligh.block.source.raduis

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.ligh.block.source.BaseActivity
import com.ligh.block.source.binding
import com.ligh.block.source.databinding.ActivityRadiusBinding


class RadiusActivity : BaseActivity() {
    override val viewBinding: ActivityRadiusBinding by binding()

    override fun initViewBinding() {
        viewBinding.view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                // 设置圆角半径为5
                outline.setRoundRect(0, 0, view.width, view.height, 50f)

            }
        }
        viewBinding.view.setClipToOutline(true)

    }
}