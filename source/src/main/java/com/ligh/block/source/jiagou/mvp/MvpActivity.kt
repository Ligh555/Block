package com.ligh.block.source.jiagou.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ligh.block.source.databinding.ActivityJiagouBinding

class MvpActivity : AppCompatActivity(),IUpdate {

    val viewBinding = ActivityJiagouBinding.inflate(this.layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    /**
     *  通过presentView去更新数据，在更新view，
     *  将需要执行的ui操作抽象化为IUpdate接口
     */
    fun initView1() {
        val presenterView = Presenter(this)
        presenterView.updateView()
    }
    override fun updateView(text: String) {
        viewBinding.etTest.setText(text)
    }
}