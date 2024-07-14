package com.ligh.block.source.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    var mData: List<T> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(onCreateView(parent, viewType))
    }

    fun onCreateView(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(parent.context).inflate(getViewLayoutId(), parent, false)

    @LayoutRes
    abstract fun getViewLayoutId(): Int

    override fun getItemCount(): Int {
        return mData.size
    }
}

class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}