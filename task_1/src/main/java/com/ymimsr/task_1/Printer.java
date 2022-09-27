package com.ymimsr.task_1;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Printer {

    private final Random random;
    private String prefix;
    private int lineLength = 10;

    public Printer(long seed) {
        this.random = new Random();
        random.setSeed(seed);
    }

    public Printer() {
        this((int) System.currentTimeMillis());
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public void printRandomText(int linesNum) {
        for (int i = 0; i < linesNum; i++) {
            System.out.println(prefix + "line #" + (i + 1) + ": " + getRandomString());
        }
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
