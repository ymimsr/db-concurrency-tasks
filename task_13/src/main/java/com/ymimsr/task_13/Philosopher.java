package com.ymimsr.task_13;

import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

    private static final int EAT_MS_RANGE = 1000;
    private static final int THINK_MS_RANGE = 2000;

    private final String name;
    private final AtomicInteger spaghettiLeft;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Fork forksMutex;

    public Philosopher(String name, AtomicInteger spaghettiLeft, Fork leftFork, Fork rightFork, Fork forksMutex) {
        this.name = name;
        this.spaghettiLeft = spaghettiLeft;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.forksMutex = forksMutex;
    }

    @Override
    public void run() {
        while (spaghettiLeft.get() > 0) {
            think();
            eat();
        }
    }

    // TODO: remake;
    private void eat() {
        System.out.println(name + " sat at the table.");
        synchronized (forksMutex) {
            try {
                while (!leftFork.isFree()) {
                    Thread.sleep(1);
                }

                takeLeftFork();

                if (rightFork.isFree()) {
                    takeRightFork();

                    // Eating
                    if (spaghettiLeft.getAndDecrement() <= 0) {
                        System.out.println(name + " didn't get any spaghetti");

                        freeRightFork();
                        freeLeftFork();
                        return;
                    }
                    System.out.println(name + " has started eating.");
                    Thread.sleep((int) (Math.random() * EAT_MS_RANGE));

                    System.out.println(name + " has finished eating.");

                    freeRightFork();
                    freeLeftFork();
                } else {
                    freeLeftFork();
                    eat();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void takeLeftFork() {
        leftFork.take();
        System.out.println(name + " has taken left fork.");
    }

    private void takeRightFork() {
        rightFork.take();
        System.out.println(name + " has taken right fork");
    }

    private void freeLeftFork() {
        leftFork.free();
        System.out.println(name + " has put his left fork on the table.");
    }

    private void freeRightFork() {
        rightFork.free();
        System.out.println(name + " has put his right fork on the table.");
    }

    private void think() {
        try {
            Thread.sleep((int) (Math.random() * THINK_MS_RANGE));
        } catch (InterruptedException ignored) {}
    }

}
