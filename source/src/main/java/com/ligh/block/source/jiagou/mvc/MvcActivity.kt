package com.ligh.block.source.jiagou.mvc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ligh.block.source.databinding.ActivityJiagouBinding

class MvcActivity : AppCompatActivity() {

    val viewBinding = ActivityJiagouBinding.inflate(this.layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
    fun initView() {
        //通过control 获取获取数据
        //再将数据填充到试图
        //这种模式下control 与view 耦合在一起，同时大量逻辑写在了视图层
        val controller = Controller()
        val text = controller.getContent()
        viewBinding.etTest.setText(text)
    }


}