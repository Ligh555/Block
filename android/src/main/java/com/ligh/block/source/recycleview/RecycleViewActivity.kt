package com.ligh.block.source.recycleview

import com.ligh.block.source.BaseActivity
import com.ligh.block.source.binding
import com.ligh.block.source.databinding.ActivityRecycleViewBinding

class RecycleViewActivity : BaseActivity() {
    override val viewBinding: ActivityRecycleViewBinding by binding()

    override fun initViewBinding() {
        viewBinding.rvTest.layoutManager?.onSaveInstanceState()
    }


}