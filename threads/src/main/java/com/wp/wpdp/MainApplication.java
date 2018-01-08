package com.wp.wpdp;

class Processor extends Thread {

    private volatile boolean running = true;

    public void run() {
        while(running) {
            System.out.println("Hello");
        }
    }

    public void shutdown() {
        running = false;
    }
}

public class MainApplication {

    public static void main(String[] args) {
        Processor proc1 = new Processor();
        proc1.start();

        proc1.shutdown();
    }
}
