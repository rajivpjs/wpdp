package com.wp.wpdp;

import java.util.Scanner;

public class ProducerConsumerLowLevel {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producing...");
            wait();
            System.out.println("Resumed");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("Consuming...");
            notify();
            Thread.sleep(5000);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final ProducerConsumerLowLevel producerConsumerLowLevel = new ProducerConsumerLowLevel();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    producerConsumerLowLevel.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    producerConsumerLowLevel.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
