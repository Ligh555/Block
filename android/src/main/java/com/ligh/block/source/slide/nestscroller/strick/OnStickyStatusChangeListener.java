package com.ligh.block.source.slide.nestscroller.strick;

import android.view.View;

/**
 * 吸顶状态改变的监听
 */
public interface OnStickyStatusChangeListener {
    /**
     * 吸顶状态改变回调
     * @param currentStickyView 当前正在吸顶的view
     * @param lastStickView 上次吸顶的view
     */
    void onStickyChanged(View currentStickyView, View lastStickView);
}
