package com.ligh.block.source.exception

import com.ligh.block.source.BaseActivity
import com.ligh.block.source.binding
import com.ligh.block.source.databinding.ActivityExceptionBinding

class ExceptionActivity : BaseActivity() {

    companion object {

        const val CRASH_MESSEAG = "crash_message"
    }

    override val viewBinding: ActivityExceptionBinding by binding()
    override fun initViewBinding() {
        viewBinding.tvContent.text = intent.getStringExtra(CRASH_MESSEAG) ?: ""
    }
}