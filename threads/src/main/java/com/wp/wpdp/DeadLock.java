package com.wp.wpdp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    private int count = 0;

    private void increment() {
        for(int i = 0; i < 10000; i++) {
            count++;
        }
    }

    class Runner {

        private java.util.concurrent.locks.Lock lock = new ReentrantLock();
        private java.util.concurrent.locks.Lock lock2 = new ReentrantLock();
        private Condition cond = lock.newCondition();

        public void firstThread() throws InterruptedException {
            lock.lock();
            lock2.lock();
            cond.await();
            try {
                increment();
            }
            finally {
                lock.unlock();
                lock2.unlock();
            }
        }

        public void secondThread() throws InterruptedException {
            lock2.lock();
            lock.lock();
            try {
                increment();
            }
            finally {
                lock.unlock();
                lock2.unlock();
            }
        }

        public void finished() {
            System.out.println("Count is " + count);
        }
    }

    public static void main(String[] args) {
        DeadLock lock = new DeadLock();
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
