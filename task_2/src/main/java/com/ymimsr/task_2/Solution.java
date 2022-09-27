package com.ymimsr.task_2;

public class Solution {

    public void start(int linesNum) {
        RandomPrinter parentPrinter = new RandomPrinter();
        RandomPrinter childPrinter = new RandomPrinter(System.currentTimeMillis() / 2);
        parentPrinter.setPrefix("Parent thread, ");
        childPrinter.setPrefix("Child thread, ");

        Thread childThread = new Thread(new PrintRunnable(childPrinter, linesNum));
        childThread.start();
        try {
            childThread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        parentPrinter.printRandomText(linesNum);
    }

    private static class PrintRunnable implements Runnable {

        private final RandomPrinter printer;
        private final int linesNum;

        public PrintRunnable(RandomPrinter printer, int linesNum) {
            this.printer = printer;
            this.linesNum = linesNum;
        }

        @Override
        public void run() {
            printer.printRandomText(linesNum);
        }

    }


}
