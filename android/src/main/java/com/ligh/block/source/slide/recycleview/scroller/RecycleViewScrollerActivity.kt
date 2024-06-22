package com.ligh.block.source.slide.recycleview.scroller

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ligh.block.source.BaseActivity
import com.ligh.block.source.binding
import com.ligh.block.source.databinding.ActivityRecycleviewScrollerBinding


class RecycleViewScrollerActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    override val viewBinding: ActivityRecycleviewScrollerBinding by binding()

    override fun initViewBinding() {
        viewBinding.rlList.apply {
            layoutManager = LinearLayoutManager(this@RecycleViewScrollerActivity)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            setRecycledViewPool(viewModel.recyclerViewPool)
            adapter = MainAdapter(this@RecycleViewScrollerActivity)

            bindScrollListener(this)
        }
    }

    private fun bindScrollListener(recyclerView: RecyclerView) {
        PagedListFetcher(recyclerView = recyclerView, lifecycleOwner = this) { page, moreCallback ->
            if (page == 1) {
                (recyclerView.adapter as? MainAdapter)?.loadTabData(lifecycleScope)
                moreCallback.invoke(false)
            }
        }
    }
}