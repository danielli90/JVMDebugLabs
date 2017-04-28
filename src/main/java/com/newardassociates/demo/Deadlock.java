package com.newardassociates.demo;

import java.util.logging.Logger;

class Deadlock {
    private static Logger LOGGER =
            Logger.getLogger(Deadlock.class.getCanonicalName());

    static Object lock1 = new Object();
    static Object lock2 = new Object();

    private static class ThreadDemo1 extends Thread {
        public void run() {
            synchronized (lock1) {
                LOGGER.info("Thread 1: Holding lock 1...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                LOGGER.info("Thread 1: Waiting for lock 2...");

                synchronized (lock2) {
                    LOGGER.info("Thread 1: Holding lock 1 & 2...");
                }
            }
        }
    }

    private static class ThreadDemo2 extends Thread {
        public void run() {
            synchronized (lock2) {
                LOGGER.info("Thread 2: Holding lock 2...");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                LOGGER.info("Thread 2: Waiting for lock 1...");

                synchronized (lock1) {
                    LOGGER.info("Thread 2: Holding lock 1 & 2...");
                }
            }
        }
    }

    static void go(String... args) {
        LOGGER.entering(Deadlock.class.getSimpleName(), "go", args);

        ThreadDemo1 T1 = new ThreadDemo1();
        ThreadDemo2 T2 = new ThreadDemo2();
        T1.start();
        T2.start();

        LOGGER.exiting(Deadlock.class.getSimpleName(), "go");
    }
}
