package com.ymimsr.task_14;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        Semaphore semC = new Semaphore(0);
        Semaphore semA = new Semaphore(0);
        Semaphore semB = new Semaphore(0);
        Semaphore semModule = new Semaphore(0);

        // C
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Started producing detail C");

                    Thread.sleep(3000);

                    System.out.println("Finished producing detail C");
                    semC.release(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // A
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Started producing detail A");

                    Thread.sleep(1000);

                    System.out.println("Finished producing detail A");
                    semA.release(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // B
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Started producing detail B");

                    Thread.sleep(2000);

                    System.out.println("Finished producing detail B");
                    semB.release(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Module
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Started producing module");

                    semA.acquire();
                    semB.acquire();

                    System.out.println("Finished producing module");
                    semModule.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Widget
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println("Started producing widget");

                    semC.acquire();
                    semModule.acquire();

                    System.out.println("Finished producing widget");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
