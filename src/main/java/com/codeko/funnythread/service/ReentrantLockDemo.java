package com.codeko.funnythread.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private static void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countWithReentrantLock() {
        Counter counter = new Counter();
        Runnable runnable1 = () -> {
            int count = 0;
            while (count < 6) {
                count = counter.getCount();
                sleep(2);
            }
        };

        Runnable runnable2 = () -> {
            int count = 0;
            while (count < 6) {
                count = counter.getCount();
                sleep(2);
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countWithoutLock() {
        Counter counter = new Counter();
        Runnable runnable1 = () -> {
            int count = 0;
            while (count < 6) {
                count = counter.getCountWithoutLock();
                sleep(1);
            }
        };

        Runnable runnable2 = () -> {
            int count = 0;
            while (count < 6) {
                count = counter.getCountWithoutLock();
                sleep(1);
            }
        };
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Counter {
    private final Lock reentrantLock = new ReentrantLock();
    private int count = 0;

    int getCount() {
        reentrantLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
            return count++;
        } finally {
            reentrantLock.unlock();
        }
    }

    int getCountWithoutLock() {
        System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
        return count++;
    }
}
