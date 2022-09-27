package com.ymimsr.task_1;

public class Solution {

    public void start(int linesNum) {
        RandomPrinter parentPrinter = new RandomPrinter();
        RandomPrinter childPrinter = new RandomPrinter(System.currentTimeMillis() / 2);
        parentPrinter.setPrefix("Parent thread, ");
        childPrinter.setPrefix("Child thread, ");

        new Thread(new PrintRunnable(childPrinter, linesNum)).start();
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
