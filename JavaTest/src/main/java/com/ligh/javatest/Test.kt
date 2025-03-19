package com.ligh.javatest

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
//    produceAndProcess()
    val exceptionHandler  = CoroutineExceptionHandler { _, exception ->
        println("Caught exception: $exception")
    }

    runBlocking {

        // 创建一个监督作用域，子协程的失败不会影响其他子协程，
        // 如果不指定，由于异常会父子协程双向传递，即使添加了exceptionHandler，最终也会抛出异常，
        // 导致兄弟异常终止
        supervisorScope {
            // 启动第一个子协程，并指定异常处理器
            val child1 = launch(exceptionHandler) {
                println("Child 1 is running")
                delay(100)
                throw RuntimeException("Child 1 failed!")
            }

            // 启动第二个子协程
            val child2 = launch {
                println("Child 2 is running")
                delay(200)
                println("Child 2 completed")
            }
            // 等待所有子协程完成
            joinAll(child1, child2)
        }
    }
    println("Parent coroutine completed")
}