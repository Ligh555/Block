package com.ligh.blog.test.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ligh.blog.R

class MyAdapter : RecyclerView.Adapter<MyAdapter.MHolder>() {

    private var mColorsList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.test_touch_print, parent, false)
        return MHolder(itemView)
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) {
        holder.view.setBackgroundColor(mColorsList[position])
    }

    override fun getItemCount(): Int {
        return mColorsList.size
    }

    fun setData(colors: List<Int>) {
        mColorsList.clear()
        colors.takeIf { it.isNotEmpty() }?.let {
            mColorsList.addAll(it)
            notifyDataSetChanged()
        }
    }

    class MHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView.findViewById<View>(R.id.view_item)
    }
}
