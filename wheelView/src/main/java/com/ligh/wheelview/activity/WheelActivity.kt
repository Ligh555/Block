package com.ligh.wheelview.activity

import com.ligh.base.activity.BaseActivity
import com.ligh.base.activity.binding
import com.ligh.wheelview.databinding.ActivityWheelBinding

class WheelActivity  : BaseActivity() {
    override val viewBinding: ActivityWheelBinding by binding()

    override fun initViewBinding() {
        viewBinding.wheel.apply {

        }
    }
}