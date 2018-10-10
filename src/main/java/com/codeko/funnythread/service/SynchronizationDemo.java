package com.codeko.funnythread.service;

import java.util.concurrent.TimeUnit;

public class SynchronizationDemo {
    public static void main(String[] args) {
        CounterSynchronization counter = new CounterSynchronization();

        Runnable runnable1 = counter::getCount;
        Runnable runnable2 = counter::bar;

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

/**
 * https://www.journaldev.com/1061/thread-safety-in-java
 *
 * When we lock on object, it acquires all fields of that object???
 * JVM synchronization works on same JVM, so if we have to do synchronize across machines, we need to use global locking mechanism
 *
 * https://stackoverflow.com/a/11986094/2574862
 * Let consider this snippet code:
 * synchronized(someObject) {
 *     //some instructions
 * }
 * It means the instructions can not be executed simultaneously by multi threads, because it needs to acquire the monitor on someObject to do so
 * Any code in your object which is not synchronized can be executed concurrently, even the monitor on this is held by a thread => synchronized(this) does
 * not lock the whole object, it just prevents multi threads execute synchronized block at the same time.
 *
 * */
class CounterSynchronization {
    private int count = 0;

    private final Object object = new Object();
    int getCount() {
        synchronized (object) {
            System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return count++;
    }

    public void bar() {
        System.out.println("inside bar method");
        count++;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after bar method");
    }

    int getCountWithoutLock() {
        System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
        return count++;
    }
}
