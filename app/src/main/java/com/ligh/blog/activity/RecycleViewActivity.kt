package com.ligh.blog.activity

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ligh.base.activity.binding
import com.ligh.base.ext.getCacheViews
import com.ligh.block.source.recycleview.DefaultAdapter
import com.ligh.blog.databinding.ActivityRecycleviewTestBinding

class RecycleViewActivity : BaseActivity() {

    override val viewBinding: ActivityRecycleviewTestBinding by binding()

    var cacheViewHolder :List<RecyclerView.ViewHolder>? =  null

    override fun initViewBinding() {
        viewBinding.rvTest.run {
            adapter = DefaultAdapter(30)
            layoutManager = LinearLayoutManager(context).apply {
            }

        }
        viewBinding.btOk.run {
            setOnClickListener {
                Log.i("TAG", "initViewBinding: ")
                viewBinding.rvTest.run {
                    val cahce = cacheViewHolder ?: getCacheViews()
                    cahce.forEach {
                        Log.i("TAG", "initViewBinding: ${it.layoutPosition}")
                    }
                }
                viewBinding.rvTest.adapter?.notifyDataSetChanged()
            }
        }
    }
}