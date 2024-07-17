package com.ligh.block.source.recycleview

import com.ligh.base.activity.binding
import com.ligh.block.source.databinding.ActivityRecycleViewBinding

class RecycleViewActivity : com.ligh.base.activity.BaseActivity() {
    override val viewBinding: ActivityRecycleViewBinding by binding()

    override fun initViewBinding() {
        viewBinding.rvTest.layoutManager?.onSaveInstanceState()
    }


}