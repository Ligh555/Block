package com.ligh.javatest.concurrent;

public class SynchronizationExample {
    
    // 静态变量，所有实例对象共享
    private static int staticCount = 0;
    // 普通变量，每个实例对象独立
    private int instanceCount = 0;

    SynchronizationExample(){
        System.out.println("构造方法开始执行");
    }

    // 静态同步方法
    public static synchronized void staticMethod() {
        System.out.println("静态方法开始执行");
        // 在静态方法中修改静态变量
        staticCount++;
        try {
            Thread.sleep(1000); // 模拟执行时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("静态方法执行结束");
    }

    // 普通同步方法
    public synchronized void instanceMethod() {
        System.out.println("普通方法开始执行");
        // 在普通方法中修改实例变量
        instanceCount++;
        try {
            Thread.sleep(1000); // 模拟执行时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("普通方法执行结束");
    }

    // 普通同步方法
    public synchronized void thisMethod() {
        synchronized (this){
            System.out.println("this方法开始执行");
            // 在普通方法中修改实例变量
            instanceCount++;
            System.out.println("this方法执行结束");
        }

    }

    public static void main() {
        final SynchronizationExample instance1 = new SynchronizationExample();
        final SynchronizationExample instance2 = new SynchronizationExample();

        // 创建两个线程分别调用静态方法和普通方法
        Thread thread1 = new Thread(() -> {
            SynchronizationExample.staticMethod();
        });

        Thread thread2 = new Thread(() -> {
            instance1.instanceMethod();
        });
        // 创建两个线程分别调用静态方法和普通方法
        Thread thread3 = new Thread(() -> {
            new SynchronizationExample();
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
