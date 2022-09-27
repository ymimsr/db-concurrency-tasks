package com.ymimsr.task_4;

public class Solution {

    public void start(int msToWait) {
        RandomPrinter printer = new RandomPrinter();
        printer.setPrefix("Child thread, ");

        Thread childThread = new Thread(new PrintRunnable(printer));
        childThread.start();
        try {
            Thread.sleep(msToWait);
            childThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class PrintRunnable implements Runnable {

        private final RandomPrinter printer;

        public PrintRunnable(RandomPrinter printer) {
            this.printer = printer;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                printer.printSingleLine();
            }
        }

    }


}
