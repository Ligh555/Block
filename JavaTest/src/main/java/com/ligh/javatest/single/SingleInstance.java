package com.ligh.javatest.single;

public class SingleInstance {

    public static SingleInstance singleInstance = null;

    public volatile SingleInstance singleInstance1 = null;

    //懒汉 线程不安全
    public SingleInstance getInstance() {
        if (singleInstance == null) {
            singleInstance = new SingleInstance();
        }
        return singleInstance;
    }

    //懒汉 线程安全
    public synchronized SingleInstance getInstance1() {
        if (singleInstance == null) {
            singleInstance = new SingleInstance();
        }
        return singleInstance;
    }

    //双重校验 线程安全
    public SingleInstance getInstance2() {
        if (singleInstance1 == null) {
            synchronized (SingleInstance.class) {
                if (singleInstance1 == null) {
                    singleInstance1 = new SingleInstance();
                }
            }
        }
        return singleInstance1;
    }


    //静态内部类实现 避免性能问题 ，同时线程安全，懒加载
    public SingleInstance getInstance3() {
        return Holder.singleinstance;
    }

    //静态内部类
    private static class Holder {
        public static final SingleInstance singleinstance = new SingleInstance();
    }


}
