package com.ligh.javatest.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {
    private int count = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int getCount() {
        lock.readLock().lock(); // 获取读锁
        try {
            return count;
        } finally {
            lock.readLock().unlock(); // 释放读锁
        }
    }

    public void increment() {
        lock.writeLock().lock(); // 获取写锁
        try {
            count++;
        } finally {
            lock.writeLock().unlock(); // 释放写锁
        }
    }
}
