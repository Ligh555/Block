package com.ligh.javatest

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

fun produceAndProcess() {
    val channel = Channel<Int>(capacity = UNLIMITED)
    runBlocking {
        // 启动第一个生产者协程
        val producer1 = launch {
            for (i in 0..100) {
                println("send $i")
                channel.send(i)
                delay(100)
            }
        }
        // 启动消费者协程
        val consumer = launch {
            for (i in channel) {
                println("receiver $i")
            }
            println("close")
        }
        // 等待第一个生产者完成
        producer1.join()
        // 启动第二个生产者协程，并在完成后关闭通道
        launch {
            for (i in 0..100) {
                println("send $i")
                channel.send(i)
                delay(100)
            }
            channel.close() // 注意此处需要主动调用 close ，consumer才会结束
        }
        // 等待消费者完成
        consumer.join()
    }
}
fun produceAndProcess1(){
    val queue: BlockingQueue<Int> = ArrayBlockingQueue(10) // 创建一个容量为10的阻塞队列
//    val queue: BlockingQueue<Int> =LinkedBlockingQueue() // 创建一个无界容器

    // 生产者线程
    Thread {
        for (i in 1..5) {
            println("Producing $i")
            queue.put(i) // 阻塞，直到队列有空间
            Thread.sleep(100)
        }
    }.start()

    // 消费者线程
    Thread {
        while (true) {
            val item = queue.take() // 阻塞，直到队列有数据
            println("Consuming $item")
            Thread.sleep(200)
        }
    }.start()
}
