package com.ligh.blog.activity

import com.ligh.base.activity.binding
import com.ligh.blog.databinding.ActivityTestBinding

class TestActivity : BaseActivity() {
    override val viewBinding: ActivityTestBinding by  binding()
    override fun initViewBinding() {

    }
}