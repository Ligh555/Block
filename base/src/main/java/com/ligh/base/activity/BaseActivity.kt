package com.ligh.base.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {

    abstract val viewBinding: ViewBinding

    abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(this::class.simpleName, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        initViewBinding()
    }

    override fun onStart() {
        Log.i(this::class.simpleName, "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        Log.i(this::class.simpleName, "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.i(this::class.simpleName, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(this::class.simpleName, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this::class.simpleName, "onDestroy: ")
    }

}