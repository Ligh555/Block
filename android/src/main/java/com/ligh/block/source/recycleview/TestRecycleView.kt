package com.ligh.block.source.recycleview

import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fang.scroll.ColorUtilis
import com.ligh.block.source.R

class TestRecycleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {


    var testSave: Parcelable? = null

    init {
        adapter = Adapter()
        layoutManager = LinearLayoutManager(context)
    }


    inner class Adapter : BaseAdapter<Int>() {

        init {
            mData = List(10) { 1 }
        }

        override fun getViewLayoutId(): Int {
            return R.layout.list_item_normal
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            if (position == mData.lastIndex) {

                repeat(4){
                    (holder.itemView as ViewGroup).run {
                        this.addView(
                            LayoutInflater.from(holder.itemView.context)
                                .inflate(
                                    R.layout.list_item_normal,
                                    holder.itemView.rootView as ViewGroup,
                                    false
                                ))
                    }
                }
                holder.itemView.setOnClickListener {
                    (it as ViewGroup).run {
                        this.removeViewAt(childCount - 1)

                    }
                }
            }
            holder.itemView.findViewById<ImageView>(R.id.iv_image)
                .setBackgroundColor(Color.parseColor(ColorUtilis.pickOneColor(position)))
            holder.itemView.findViewById<TextView>(R.id.tv_position).text = position.toString()
        }

    }

}