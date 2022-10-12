package com.ymimsr.task_11;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Semaphore sem1 = new Semaphore(1);
        Semaphore sem2 = new Semaphore(1);

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                if (sem2.tryAcquire())
                    sem1.release();

                System.out.println("Child Thread Output: " + i);

                if (sem1.tryAcquire())
                    sem2.release();
            }
        }).start();

        for (int i = 1; i <= 10; i++) {
            if (sem2.tryAcquire())
                sem1.release();

            System.out.println("Parent Thread Output: " + i);

            if (sem1.tryAcquire())
                sem2.release();
        }
    }

}
