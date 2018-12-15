package com.codeko.funnythread.service;

/**
 * All these low-level APIs, such as wait(), notify() and notifyAll() – are traditional methods that work well,
 * but higher-level mechanism are often simpler and better – such as Java’s native Lock and Condition interfaces
 * */
public class WaitAndNotifyDemo {
    public static void main(String[] args) {
        Data data = new Data();
        Sender sender = new Sender(data);
        Receiver receiver = new Receiver(data);

        Thread thread1 = new Thread(sender);
        Thread thread2 = new Thread(receiver);

        thread1.start();
        thread2.start();
    }
}

class Receiver implements Runnable {

    private Data data;

    Receiver(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        String message;
        do {
            message = data.receive();
            System.out.println("Received: " + message);
        } while (!Data.END_MESSAGE.equals(message));
        System.err.println("Done at receiver side");
    }
}
class Sender implements Runnable {

    private Data data;

    Sender(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        String[] messages = new String[]{
                "First message",
                "Second message",
                "Third message",
                Data.END_MESSAGE
        };
        for (String message :
                messages) {
            data.send(message);
        }
        System.err.println("Done at sender side");
    }
}
class Data {

    static final String END_MESSAGE = "End";

    private String content;

    private boolean isTransferring = true;

    synchronized void send(String content) {
        while (!isTransferring) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isTransferring = false;
        this.content = content;
        notifyAll();
    }

    synchronized String receive() {
        while (isTransferring) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isTransferring = true;
        notifyAll();
        return this.content;
    }
}
