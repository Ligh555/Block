package com.ligh.block.source.hotfix

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ligh.block.source.R


class MainActivityHotfix : AppCompatActivity() {

    lateinit var tvHotfix :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hotfix)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initHotFix()
        initView()
    }

    fun initHotFix(){
       HotFixHelper.initHotFix(this)
    }

    fun initView(){
        tvHotfix = findViewById(R.id.tv_hot)
        tvHotfix.text = Test.getText()
    }

}