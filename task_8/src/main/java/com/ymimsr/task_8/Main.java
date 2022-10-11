package com.ymimsr.task_8;

public class Main {

    public static void main(String[] args) {
        int threadNum = Integer.parseInt(args[0]);

        Solution solution = new Solution(threadNum);
        solution.startCountingPi();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Calculated pi: " + solution.getPi())));
    }

}
