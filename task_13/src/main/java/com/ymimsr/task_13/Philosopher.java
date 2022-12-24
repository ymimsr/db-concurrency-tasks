package com.ymimsr.task_13;

import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

    private static final int EAT_MS_RANGE = 2000;
    private static final int THINK_MS_RANGE = 1000;

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

        tryToEat();
    }

    private void tryToEat() {
        try {
            synchronized (forksMutex) {
                if (leftFork.isFree()) {
                    takeLeftFork();
                    if (rightFork.isFree()) {
                        takeRightFork();
                        forksMutex.notifyAll();
                    } else {
                        freeLeftFork();
                        forksMutex.wait();
                        tryToEat();
                    }
                } else {
                    forksMutex.wait();
                    tryToEat();
                }
            }

            System.out.println(name + " obtained both forks.");

            System.out.println(name + " starts eating.");
            Thread.sleep((int) (Math.random() * EAT_MS_RANGE));
            spaghettiLeft.decrementAndGet();
            System.out.println(name + " finished eating.");

            freeLeftFork();
            freeRightFork();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
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
