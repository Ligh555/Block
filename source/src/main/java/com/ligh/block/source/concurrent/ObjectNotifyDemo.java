package com.ligh.block.source.concurrent;

public class ObjectNotifyDemo {
    Object lock = new Object();

    void test1(){
        new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (lock){
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("test1" + "wait");
            }
        }.start();
    }

    void test2(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("test2" + "notify");
                synchronized (lock){
                    lock.notifyAll();
                }
            }
        }.start();
    }

    void  test(){
        test1();
        test2();
    }
}
