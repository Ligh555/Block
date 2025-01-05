package com.ligh.blog.activity

import android.widget.Toast
import com.ligh.base.activity.binding
import com.ligh.blog.databinding.ActivityTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.suspendCoroutine

class TestActivity : BaseActivity() {
    override val viewBinding: ActivityTestBinding by  binding()
    override fun initViewBinding() {

        viewBinding.run {
            btLock.setOnClickListener {
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        suspendCoroutine {

                        }
                    }
                }
            }

            btNormal.setOnClickListener {
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                       withContext(Dispatchers.Main){
                           Toast.makeText(this@TestActivity,"ceshishis",Toast.LENGTH_SHORT).show()
                       }
                    }
                }
            }

            btOther.setOnClickListener {
                GlobalScope.launch {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@TestActivity,"ceshishis",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}