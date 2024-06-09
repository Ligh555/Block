package com.ligh.javatest.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequentialExecution {
    private static Lock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    private static Condition condition3 = lock.newCondition();
    private static volatile int currentThread = 1;

    public static void main() {
        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        if (currentThread == 1) {
                            System.out.println("Thread 1 - Test 1");
                            currentThread = 2;
                            condition2.signal();
                        } else {
                            condition1.await();
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        if (currentThread == 2) {
                            System.out.println("Thread 2 - Test 2");
                            currentThread = 3;
                            condition3.signal();
                        } else {
                            condition2.await();
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                while (true) {
                    lock.lock();
                    try {
                        if (currentThread == 3) {
                            System.out.println("Thread 3 - Test 3");
                            currentThread = 1;
                            condition1.signal();
                        } else {
                            condition3.await();
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
