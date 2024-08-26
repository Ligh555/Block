package com.ligh.block.source.coordinatorLayout

import androidx.recyclerview.widget.LinearLayoutManager
import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.block.source.databinding.ActivityCollapsingToolbarLayout1Binding
import com.ligh.block.source.recycleview.DefaultAdapter

class CollapsingToolbarLayoutActivity : BaseActivity() {
    override val viewBinding: ActivityCollapsingToolbarLayout1Binding by binding()

    override fun initViewBinding() {
        viewBinding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = DefaultAdapter(20)
        }

        val appBarLayout = viewBinding.appBar

        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val offsetPercentage = Math.abs(verticalOffset).toFloat() / totalScrollRange

            if (offsetPercentage >= 0.5) {
                // 计算alpha值，0.5到1之间，alpha从0.3变化到1
                val alpha = (offsetPercentage - 0.5f) * 2 * 0.7f + 0.3f
                viewBinding.title.alpha = alpha
            } else {
                // 在达到50%之前隐藏标题
                viewBinding.title.alpha = 0f
            }
            // 当 AppBarLayout 的 offset 是负值时，表示已经开始收缩
            viewBinding.scroller.run {
                val lp = layoutParams
                lp.height = viewBinding.root.height - viewBinding.toolbar.height
                layoutParams = lp
            }
        }

        // 设置返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}