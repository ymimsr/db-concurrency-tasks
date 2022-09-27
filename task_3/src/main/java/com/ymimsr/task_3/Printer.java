package com.ymimsr.task_3;

public class Printer {

    private final String prefix;

    public Printer(String prefix) {
        this.prefix = prefix;
    }

    public Printer() {
        this("");
    }

    public void printText(String[] text) {
        for (String line : text) {
            System.out.println(prefix + line);
        }
    }

}
