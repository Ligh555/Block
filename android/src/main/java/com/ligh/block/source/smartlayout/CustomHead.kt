package com.ligh.block.source.smartlayout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.ligh.block.source.R
import com.scwang.smartrefresh.layout.api.RefreshInternal
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.internal.InternalAbstract

class CustomHead @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : InternalAbstract(context, attrs, defStyleAttr), RefreshInternal {

    private val mTitleContext: TextView by lazy {
        findViewById(R.id.tv_content)
    }
    private val mProgressView: ImageView by lazy {
        findViewById<ImageView?>(R.id.iv_process).apply {
            animate().interpolator = LinearInterpolator()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mProgressView.animate().cancel()
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        kernel.requestDrawBackgroundFor(this, getBackgroundColor())
    }

    private fun getBackgroundColor(): Int {
        return (background as? ColorDrawable)?.color ?:Color.WHITE
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        mProgressView.animate().rotation(36000f).duration = 100000L
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        onStartAnimator(refreshLayout, height, maxDragHeight)
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        mTitleContext.text = if (success) "mTextFinish" else "mTextFailed"
        mProgressView.animate().rotation(0f).duration = 0L
        return 500 // Delay of 500 milliseconds before bounce-back
    }

    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        mTitleContext.text = when (newState) {
            RefreshState.PullDownToRefresh -> "mTextPulling"
            RefreshState.Refreshing, RefreshState.RefreshReleased -> "mTextRefreshing"
            RefreshState.ReleaseToRefresh -> "mTextRelease"
            RefreshState.ReleaseToTwoLevel -> "mTextSecondary"
            RefreshState.Loading -> "mTextLoading"
            else -> mTitleContext.text // Preserve current text for other states
        }
    }
}
