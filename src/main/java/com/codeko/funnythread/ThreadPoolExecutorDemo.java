package com.codeko.funnythread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),
                (r, executor) -> System.out.println("Task " + r.toString() + " is rejected from " + executor.toString())
        );

        ThreadCustom thread1 = new ThreadCustom("task1", 60*1000);
        ThreadCustom thread2 = new ThreadCustom("task2",10*1000);
        ThreadCustom thread3 = new ThreadCustom("task3",2000);
        ThreadCustom thread4 = new ThreadCustom("task4",2000);
        ThreadCustom thread5 = new ThreadCustom("task5",2000);
        ThreadCustom thread6 = new ThreadCustom("task6",2000);
        ThreadCustom thread7 = new ThreadCustom("task7",2000);
        //execute
        threadPoolExecutor.submit(thread1);//core
        threadPoolExecutor.submit(thread2);//queue
        threadPoolExecutor.submit(thread3);//queue
        Future future = threadPoolExecutor.submit(thread4);//max
        threadPoolExecutor.submit(thread5);//reject
        while (true) {
            if (future.isDone()) {
                System.out.println(Thread.currentThread().getName() + ":::Add thread 6 and 7");
                threadPoolExecutor.submit(thread6);
                threadPoolExecutor.submit(thread7);
                break;
            }
        }

        threadPoolExecutor.shutdown();
    }
}

class ThreadCustom implements Callable {
    private String name;
    private long time;

    public ThreadCustom(String name, long time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public Object call() {
        System.out.println(name + ":::work  started");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println(name + ":::work  finished");
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
