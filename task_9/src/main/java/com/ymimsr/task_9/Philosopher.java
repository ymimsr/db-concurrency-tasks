package com.ymimsr.task_9;

import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

    private static final int EAT_MS_RANGE = 1000;
    private static final int THINK_MS_RANGE = 2000;

    private final String name;
    private final AtomicInteger spaghettiLeft;
    private final Fork leftFork;
    private final Fork rightFork;

    public Philosopher(String name, AtomicInteger spaghettiLeft, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.spaghettiLeft = spaghettiLeft;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (spaghettiLeft.get() > 0) {
            think();
            eat();
        }
    }

    private void eat() {
        System.out.println(name + " sat at the table.");
        synchronized (leftFork) {
            System.out.println(name + " has taken left fork.");
            synchronized (rightFork) {
                System.out.println(name + " has taken right fork");

                // Eating
                if (spaghettiLeft.getAndDecrement() <= 0) {
                    System.out.println(name + " didn't get any spaghetti");
                    return;
                }
                System.out.println(name + " has started eating.");
                try {
                    Thread.sleep((int) (Math.random() * EAT_MS_RANGE));
                } catch (InterruptedException ignored) {}

                System.out.println(name + " has finished eating.");
            }
        }
    }

    private void think() {
        try {
            Thread.sleep((int) (Math.random() * THINK_MS_RANGE));
        } catch (InterruptedException ignored) {}
    }

}
