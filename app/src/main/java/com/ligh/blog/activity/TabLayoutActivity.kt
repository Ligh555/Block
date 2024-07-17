package com.ligh.blog.activity

import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.ligh.base.activity.binding
import com.ligh.blog.databinding.ActivityMain3Binding

class TabLayoutActivity : BaseActivity() {

    override val viewBinding: ActivityMain3Binding by binding()


    override fun initViewBinding() {

    }

    override fun onResume() {
        super.onResume()
        initTabLayout()
    }

    private fun initTabLayout() {

        repeat(3) {
            val tab = viewBinding.tabLayout1.newTab()
//            var textView = TextView(this).apply {
//                text = "测试$it"
//            }
//            tab.customView = textView
            tab.text = "测试$it"
            viewBinding.tabLayout1.addTab(tab)
        }
        repeat(3) {
            val tab = viewBinding.tabLayout6.newTab()
//            var textView = TextView(this).apply {
//                text = "测试$it"
//            }
//            tab.customView = textView
            tab.text = "测试$it"
            viewBinding.tabLayout6.addTab(tab)
        }
        repeat(3) {
            val tab = viewBinding.tab1.newTab()
//            var textView = TextView(this).apply {
//                text = "测试$it"
//            }
//            tab.customView = textView
            var str =""
            repeat(it+1){
                str += "测试$it"
            }
            tab.text = str
            viewBinding.tab1.addTab(tab)
        }

        viewBinding.tabLayout6.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Toast.makeText(this@TabLayoutActivity, "select${tab.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                Toast.makeText(this@TabLayoutActivity, "un${tab.text}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                Toast.makeText(this@TabLayoutActivity, "re${tab.text}", Toast.LENGTH_SHORT).show()
            }

        })

    }
}