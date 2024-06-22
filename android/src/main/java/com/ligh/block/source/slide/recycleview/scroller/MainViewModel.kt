package com.ligh.block.source.slide.recycleview.scroller

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

/**
 * @author fangkw on 2020/10/24
 **/
class MainViewModel : ViewModel() {
    val recyclerViewPool = RecyclerView.RecycledViewPool()

    override fun onCleared() {
        super.onCleared()
        recyclerViewPool.clear()
    }
}