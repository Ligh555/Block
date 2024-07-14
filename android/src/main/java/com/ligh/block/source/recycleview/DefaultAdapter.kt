package com.ligh.block.source.recycleview

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import com.fang.scroll.ColorUtilis
import com.ligh.block.source.R

class DefaultAdapter(count: Int) : BaseAdapter<Int>() {

    init {
        mData = List(count) { 1 }
    }

    override fun getViewLayoutId(): Int {
        return R.layout.list_item_normal
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.findViewById<ImageView>(R.id.iv_image)
            .setBackgroundColor(Color.parseColor(ColorUtilis.pickOneColor(position)))
        holder.itemView.findViewById<TextView>(R.id.tv_position).text = position.toString()
    }
}