package com.ymimsr.task_15;

public class Main {

    public static void main(String[] args) {
        int fromP = Integer.parseInt(args[0]);
        String address = args[1];
        int toP = Integer.parseInt(args[2]);

        new Server(fromP, toP, address).start();
    }

}
