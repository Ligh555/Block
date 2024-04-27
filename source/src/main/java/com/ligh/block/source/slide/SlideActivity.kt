package com.ligh.block.source.slide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ligh.block.source.R
import com.ligh.block.source.databinding.ActivitySlideBinding

class SlideActivity : AppCompatActivity() {

    private lateinit var viewBinding :ActivitySlideBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initView()
    }


    private fun initView() {
        viewBinding.root.targetView = viewBinding.space
        viewBinding.rvTest.run {
            layoutManager = LinearLayoutManager(this@SlideActivity)

            adapter = object : RecyclerView.Adapter<TheViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheViewHolder {
                    return TheViewHolder(
                        LayoutInflater.from(this@SlideActivity)
                            .inflate(R.layout.test_item, parent, false)
                    )
                }

                override fun onBindViewHolder(holder: TheViewHolder, position: Int) {
                    holder.itemView.findViewById<TextView>(R.id.tv_test).text = "$position"
                }

                override fun getItemCount(): Int {
                    return 100
                }

            }
        }
    }

    class TheViewHolder(itemView: View) : ViewHolder(itemView)
}