package com.ymimsr.task_3;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int threadNum = 4, linesNum = 3;
        String[][] text = new String[threadNum][linesNum];
        for (int i = 0; i < threadNum; i++) {
            for (int k = 0; k < linesNum; k++) {
                text[i][k] = getRandomString(40);
            }
        }

        solution.start(text);
    }

    private static String getRandomString(int lineLength) {
        int leftLimit = 97;
        int rightLimit = 122;

        return new Random().ints(leftLimit, rightLimit + 1)
                .limit(lineLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
