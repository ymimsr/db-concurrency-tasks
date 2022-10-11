package com.ymimsr.task_3;

public class Solution {

    public void start(String[][] args) {
        for (int i = 0; i < args.length; i++) {
            Printer printer = new Printer("Thread #" + (i + 1) + ": ");
            new Thread(new PrintRunnable(printer, args[i])).start();
        }
    }

    private static class PrintRunnable implements Runnable {

        private final Printer printer;
        private final String[] text;

        public PrintRunnable(Printer printer, String[] text) {
            this.printer = printer;
            this.text = text;
        }

        @Override
        public void run() {
            printer.printText(text);
        }

    }


}
