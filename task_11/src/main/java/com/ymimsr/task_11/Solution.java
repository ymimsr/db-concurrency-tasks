package com.ymimsr.task_11;

public class Solution {

    public void start(int linesNum) {
        RandomPrinter printer = new RandomPrinter();

        new Thread(new PrintRunnable(printer, linesNum, "Child thread")).start();
        printer.printRandomText("Parent thread", linesNum);
    }

    private static class PrintRunnable implements Runnable {

        private final RandomPrinter printer;
        private final int linesNum;
        private final String threadName;

        public PrintRunnable(RandomPrinter printer, int linesNum, String threadName) {
            this.printer = printer;
            this.linesNum = linesNum;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            printer.printRandomText(threadName, linesNum);
        }

    }


}
