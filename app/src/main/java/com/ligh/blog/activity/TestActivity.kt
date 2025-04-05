package com.ligh.blog.activity

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.ligh.base.activity.binding
import com.ligh.base.ext.getBitMap
import com.ligh.blog.R
import com.ligh.blog.databinding.ActivityTestBinding
import com.ligh.blog.test.viewpager.MyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.suspendCoroutine

class TestActivity : BaseActivity() {
    companion object {
        var testBitmap: Bitmap? = null
    }

    override val viewBinding: ActivityTestBinding by binding()
    val testHandler = Handler(Looper.getMainLooper())

    fun test() {
        val testArray = Array(100) { "test" }
        for (i in 0..testArray.lastIndex) {
            testArray[i] = testArray[i] + i
        }
        Log.i("TAG", "test: ${testArray.size}")
        testHandler.postDelayed(
            {
                test()
            }, 16
        )
    }

    override fun initViewBinding() {

        viewBinding.run {
            btLock.setOnClickListener {
                test()
            }

            btNormal.setOnClickListener {
                Thread {
                    simulateOOMAnr()
                }.start()

            }

            btOther.setOnClickListener {
                testBitmap?.recycle()
                Toast.makeText(
                    this@TestActivity,
                    (testBitmap?.isRecycled == true).toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewPager.adapter = MyAdapter().apply {
                setData(arrayListOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN))
            }
        }


    }

    fun simulateIoAnr() {
        // 在主线程执行大文件写入
        val file = File(filesDir, "large_file.dat")
        val outputStream = FileOutputStream(file)

        // 写入大量数据(约100MB)
        val buffer = ByteArray(1024 * 1024) // 1MB
        repeat(100) {
            outputStream.write(buffer) // 每次写入1MB
            Log.d("IO_ANR", "Written ${it + 1}MB")
        }
        outputStream.close()
    }

    fun simulateOOMAnr() {
        val list = mutableListOf<ByteArray>()
        while (true) {
            // 每次分配 1MB 内存
            list.add(ByteArray(1024 * 1024)) // 1MB
            Log.d("OOM_TEST", "Allocated: ${list.size} MB")
        }
    }
}