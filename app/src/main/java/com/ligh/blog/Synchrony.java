package com.ligh.blog;

class Monitor {

}

public class Synchrony {

    Monitor monitor = new Monitor();


    synchronized void syncFunction() {
        System.out.println("syncFunction");
    }

    void test() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (this) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("test");
                }
            }
        };
        thread.start();
    }

    public static void main(String[] args) {
        Synchrony synchrony = new Synchrony();
        synchrony.test();
        synchrony.syncFunction();
    }
}
