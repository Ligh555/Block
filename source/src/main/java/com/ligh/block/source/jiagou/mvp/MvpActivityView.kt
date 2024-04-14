package com.ligh.block.source.jiagou.mvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ligh.block.source.databinding.ActivityJiagouBinding

class MvpActivityView : AppCompatActivity() {


    val viewBinding = ActivityJiagouBinding.inflate(this.layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    /**
     *  通过presentView去更新数据，在更新view，但是由于直接获取view，难以复用以及兼容性较差
     *  因此将present 持有的view抽象化，也就是接口化，抽离为某一类行为
     */
    fun initView1() {
        val presenterView = PresenterView(viewBinding.etTest)
        presenterView.updateView()
    }

}