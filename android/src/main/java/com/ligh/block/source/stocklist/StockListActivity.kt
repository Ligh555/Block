package com.ligh.block.source.stocklist

import androidx.recyclerview.widget.LinearLayoutManager
import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.block.source.databinding.ActivityStockListBinding
import com.ligh.block.source.recycleview.DefaultAdapter

class StockListActivity : BaseActivity() {
    override val viewBinding: ActivityStockListBinding by binding()

    override fun initViewBinding() {
        viewBinding.rvTest.run {
            layoutManager = LinearLayoutManager(context)
            adapter = DefaultAdapter(20)
        }
    }
}