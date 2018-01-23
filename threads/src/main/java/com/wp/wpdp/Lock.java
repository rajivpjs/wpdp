package com.wp.wpdp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {

    private int count = 0;

    private void increment() {
        for(int i = 0; i < 10000; i++) {
            count++;
        }
    }

    class Runner {

        private java.util.concurrent.locks.Lock lock = new ReentrantLock();
        private Condition cond = lock.newCondition();

        public void firstThread() throws InterruptedException {
            lock.lock();

            System.out.println("First Thread...");
            cond.await();
            System.out.println("Resuming...");
            try {
                increment();
            }
            finally {
                lock.unlock();
            }
        }

        public void secondThread() throws InterruptedException {
            lock.lock();

            System.out.println("Second Thread...");
            Thread.sleep(10000);
            cond.signal();
            try {
                increment();
            }
            finally {
                lock.unlock();
            }
        }

        public void finished() {
            System.out.println("Count is " + count);
        }
    }

    public static void main(String[] args) {
        Lock lock = new Lock();
        final Runner r = lock.new Runner();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    r.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    r.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            r.finished();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
