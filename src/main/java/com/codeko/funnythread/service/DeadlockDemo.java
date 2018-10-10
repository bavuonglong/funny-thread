package com.codeko.funnythread.service;

public class DeadlockDemo {
    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        Thread thread1 = new Thread(new SyncThread(object1, object2), "thread 1");
        Thread thread2 = new Thread(new SyncThread(object2, object3), "thread 2");
        Thread thread3 = new Thread(new SyncThread(object3, object1), "thread 3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SyncThread implements Runnable {

    private final Object object1;
    private final Object object2;

    public SyncThread(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name +" is acquiring lock on " + object1);
        synchronized (object1) {
            System.out.println(name + " acquired lock on " + object1);
            doSomeHeavyWork();
        }
        System.out.println(name + " released lock on " + object2);

        System.out.println(name +" is acquiring lock on " + object2);
        synchronized (object2) {
            System.out.println(name + " acquired lock on " + object2);
            doSomeHeavyWork();
        }
        System.out.println(name + " released lock on " + object2);
        System.out.println("Finish");
    }

    private void doSomeHeavyWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
