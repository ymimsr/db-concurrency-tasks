package com.ymimsr;

import java.util.Random;

public class RandomPrinter {

    private final Random random;
    private int lineLength = 10;

    public RandomPrinter(long seed) {
        this.random = new Random();
        random.setSeed(seed);
    }

    public RandomPrinter() {
        this((int) System.currentTimeMillis());
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public synchronized void printRandomText(String prefix, int linesNum) {
        try {
            for (int i = 0; i < linesNum; i++) {
                System.out.println(prefix + ", line #" + (i + 1) + ": " + getRandomString());
                notify();
                wait();
            }
            notify();
        } catch (InterruptedException ignored) {}
    }

    private String getRandomString() {
        int leftLimit = 97;
        int rightLimit = 122;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(lineLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
