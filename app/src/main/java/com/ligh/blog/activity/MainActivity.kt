package com.ligh.blog.activity

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ligh.base.activity.binding
import com.ligh.blog.R
import com.ligh.blog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override val viewBinding: ActivityMainBinding by binding()

    override fun initViewBinding() {
        viewBinding.recycleView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter  = object  : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.test1, parent, false)
                    return MyViewHolder(view)
                }

                override fun getItemCount(): Int {
                    return 50
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                }

            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {//返回键
                return if (supportFragmentManager.backStackEntryCount < 1) {
                    AlertDialog.Builder(this)
                        .setTitle("退出应用")
                        .setMessage("确定要退出应用吗？")
                        .setPositiveButton("确定") { _, _ ->
                            finish()
                        }
                        .setNegativeButton("取消", null)
                        .show()
                    true
                } else {
                    supportFragmentManager.popBackStack()
                    true
                }
            }

            KeyEvent.KEYCODE_HOME -> {//HOME键
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
            }

            KeyEvent.KEYCODE_NUMPAD_0 -> {//数字键
                Toast.makeText(this, "F4", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.test, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 10
        }
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}