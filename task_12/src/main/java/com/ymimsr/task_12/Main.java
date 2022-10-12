package com.ymimsr.task_12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Main {

    private static final LinkedList<String> linkedList = new LinkedList<>();

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        linkedList.add("Sync block");

        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    synchronized (linkedList.getLast()) {
                        for (int i = 0; i < linkedList.size() - 1; i++) {
                            for (int k = 0; k < linkedList.size() - 2; k++) {
                                if (linkedList.get(k + 1).compareTo(linkedList.get(k)) < 0) {
                                    String temp = linkedList.get(k);
                                    linkedList.set(k, linkedList.get(k + 1));
                                    linkedList.set(k + 1, temp);
                                }
                            }
                        }
                        System.out.println("Sorted!");
                    }
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }).start();

        try {
            while (true) {
                String newLine = reader.readLine();
                if (newLine.isEmpty())
                    printState();
                String[] linesToAdd = partition(newLine);

                addLines(linesToAdd);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String[] partition(String line) {
        int partitionLength = line.length() / 80 + (line.length() % 80 == 0 ? 0 : 1);
        String[] partition = new String[partitionLength];
        for (int i = 0; i < partitionLength; i++) {
            if (i == partitionLength - 1) {
                partition[i] = line.substring(i * 80);
                break;
            }
            partition[i] = line.substring(i * 80, (i + 1) * 80);
        }

        return partition;
    }

    private static void addLines(String[] lines) {
        for (String line : lines) {
            synchronized (linkedList.getLast()) {
                linkedList.addFirst(line);
            }
        }
    }

    private static void printState() {
        synchronized (linkedList.getLast()) {
            for (String line : linkedList.subList(0, linkedList.size() - 1)) {
                System.out.println(line);
            }
        }
    }

}
