package com.ligh.blog.activity

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_CANCEL
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_OUTSIDE
import android.view.MotionEvent.ACTION_UP
import com.ligh.base.activity.binding
import com.ligh.blog.databinding.ActivityMain4Binding

class TouchEventActivity : BaseActivity() {

    companion object{
        const val TAG = "MainActivity4"
    }

    override val viewBinding: ActivityMain4Binding by binding()

    @SuppressLint("ClickableViewAccessibility")
    override fun initViewBinding() {
        viewBinding.bt.apply {
            setOnClickListener {
                Log.i(TAG, "initViewBinding: onclick bt ")
            }

            setOnTouchListener { v, event ->
                Log.i(TAG, "initViewBinding: touch bt ${getEventString(event)}")
                return@setOnTouchListener false
            }
        }
        viewBinding.main.apply {
            viewBinding.ll.apply {
                setOnClickListener {
                    Log.i(TAG, "initViewBinding: onclick main")
                }

                setOnTouchListener { v, event ->
                    Log.i(TAG, "initViewBinding: touch main  ${getEventString(event)}")
                    return@setOnTouchListener false
                }
            }
        }

        viewBinding.ll.apply {
            setOnClickListener {
                Log.i(TAG, "initViewBinding: onclick ll")
            }

            setOnTouchListener { v, event ->
                Log.i(TAG, "initViewBinding: touch ll  ${getEventString(event)}")
                return@setOnTouchListener false
            }
        }
    }

    private fun getEventString(event : MotionEvent) :String{
        return when (event.action){
            ACTION_MOVE -> "ACTION_MOVE"
            ACTION_UP -> "ACTION_UP"
            ACTION_CANCEL ->"ACTION_CANCEL"
            ACTION_DOWN ->"ACTION_DOWN"
            ACTION_OUTSIDE ->"ACTION_OUTSIDE"
            else ->"other"
        }
    }
}