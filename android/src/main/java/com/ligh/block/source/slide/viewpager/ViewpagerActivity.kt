package com.ligh.block.source.slide.viewpager

import android.graphics.Color
import com.ligh.base.activity.binding
import com.ligh.block.source.databinding.ActivityViewpagerBinding

class ViewpagerActivity : com.ligh.base.activity.BaseActivity() {
    private val list = arrayListOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN)
    override val viewBinding: ActivityViewpagerBinding by binding()

    override fun initViewBinding() {
        val adapter = MyAdapter()
        adapter.setData(list)
        viewBinding.viewPager.adapter = adapter
    }

}