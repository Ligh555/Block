package com.ligh.block.source.slide.viewpager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ligh.block.source.R
import com.ligh.block.source.databinding.InternalViewpagerBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.MHolder>() {

    private var mColorsList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        val itemView = if (viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.internal_viewpager, parent, false)
            view
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        }
        return MHolder(itemView)
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        if (position == mColorsList.lastIndex) {
            val adapter = MyAdapter()
            adapter.setData(arrayListOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN))
            InternalViewpagerBinding.bind(holder.itemView).viewPager.adapter = adapter

        } else {
            holder.view.setBackgroundColor(mColorsList[position])
        }

    }

    override fun getItemCount(): Int {
        return mColorsList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == mColorsList.lastIndex) {
            return 1
        }
        return 0
    }

    fun setData(colors: List<Int>) {
        mColorsList.clear()
        mColorsList.addAll(colors)
        notifyDataSetChanged()
    }

    class MHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView.findViewById<View>(R.id.view_item)
    }
}
