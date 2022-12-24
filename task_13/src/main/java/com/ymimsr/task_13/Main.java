package com.ymimsr.task_13;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int SPAGHETTI_AMOUNT = 5;

    public static void main(String[] args) {
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Fork forksMutex = new Fork();

        AtomicInteger spaghettiLeft = new AtomicInteger(SPAGHETTI_AMOUNT);

        Philosopher philosopher1 = new Philosopher("Aristotle", spaghettiLeft, fork1, fork2, forksMutex);
        Philosopher philosopher2 = new Philosopher("Socrates", spaghettiLeft, fork2, fork3, forksMutex);
        Philosopher philosopher3 = new Philosopher("Kant", spaghettiLeft, fork3, fork4, forksMutex);
        Philosopher philosopher4 = new Philosopher("Confucius", spaghettiLeft, fork4, fork5, forksMutex);
        Philosopher philosopher5 = new Philosopher("Descartes", spaghettiLeft, fork5, fork1, forksMutex);

        new Thread(philosopher1).start();
        new Thread(philosopher2).start();
        new Thread(philosopher3).start();
        new Thread(philosopher4).start();
        new Thread(philosopher5).start();
    }

}
