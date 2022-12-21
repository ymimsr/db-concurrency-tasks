package com.ymimsr.task_11;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(1);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    sem1.acquire();

                    System.out.println("Child Thread Output: " + i);

                    sem2.release();
                } catch (InterruptedException ignored) {}
            }
        }).start();

        for (int i = 1; i <= 10; i++) {
            try {
                sem2.acquire();

                System.out.println("Parent Thread Output: " + i);

                sem1.release();
            } catch (InterruptedException ignored) {}
        }
    }

}
