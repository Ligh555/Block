package com.ligh.base.ext

import android.util.Log
import androidx.recyclerview.widget.RecyclerView

/**
 * 获取 RecyclerView 的 CacheViews 中的 ViewHolder 列表
 */
fun RecyclerView.getCacheViews(): List<RecyclerView.ViewHolder> {
    return try {
        // 1. 获取 mRecycler 字段
        val recyclerField = RecyclerView::class.java.getDeclaredField("mRecycler")
        recyclerField.isAccessible = true
        val recycler = recyclerField.get(this)

        // 2. 获取 Recycler 类中的 mCachedViews 字段
        val recyclerClass = Class.forName("androidx.recyclerview.widget.RecyclerView\$Recycler")
        val cachedViewsField = recyclerClass.getDeclaredField("mCachedViews")
        cachedViewsField.isAccessible = true

        // 3. 获取并返回 mCachedViews 列表
        @Suppress("UNCHECKED_CAST")
        cachedViewsField.get(recycler) as? ArrayList<RecyclerView.ViewHolder> ?: emptyList()
    } catch (e: Exception) {
        Log.e("RecyclerViewCache", "获取 mCachedViews 失败", e)
        emptyList()
    }
}
