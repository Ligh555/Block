package com.ligh.block.source.tablayout

import com.ligh.base.activity.binding
import com.ligh.block.source.databinding.ActivityTabLayoutBinding

class TabLayoutActivity : com.ligh.base.activity.BaseActivity() {
    override val viewBinding: ActivityTabLayoutBinding by binding()

    override fun initViewBinding() {
        viewBinding.tabLayout1.run {
            listOf("测试","测试测试","测试","测试","测试测试","测试","测试","测试测试","测试").onEach {
                addTab(newTab().setText(it).apply {
                    view.setOnLongClickListener { true } //去除长按提示toast
                })
            }

        }
    }

}