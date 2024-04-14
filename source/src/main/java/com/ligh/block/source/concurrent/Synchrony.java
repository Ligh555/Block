package com.ligh.block.source.concurrent;

import static java.lang.Thread.sleep;


public class Synchrony {

    byte[] monitor = new byte[0];


    void stopThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (isInterrupted()) {
                        break;
                    }
                    System.out.println("while");
                }

            }
        };
        thread.start();
        //采用中断并不会立即停止线程，这里只是设置一个标志位，具体是否停止由操作系统决定
        //但是编码时可根据这个标志位来判断是否结束线程
        try {
            sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        thread.interrupt();
    }

    void MoreLock(){
        synchronized (this){
            synchronized (monitor){

            }
        }
    }


    synchronized void syncFunction() {
        System.out.println("syncFunction");
    }

    void print(String flag) {
        synchronized (this) {
            try {
                sleep(1000);
                System.out.println(flag);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void test() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                print("test");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                syncFunction();
            }
        }.start();
    }
}