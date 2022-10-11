package com.ymimsr.task_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Solution {

    private static double countPi(int threadNum, int iterations) {
        List<FutureTask<Double>> taskList = new ArrayList<>();
        int[] partition = getPartition(threadNum, iterations);

        for (int i = 0; i < threadNum; i++) {
            Callable<Double> piCalculator = new PiCalculator(partition[i], partition[i + 1]);
            FutureTask<Double> calculationTask = new FutureTask<>(piCalculator);
            taskList.add(calculationTask);

            new Thread(calculationTask).start();
        }

        double sum = 0;
        for (FutureTask<Double> calculationTask : taskList) {
            try {
                sum += calculationTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return 4 * sum;
    }

    private static int[] getPartition(int threadNum, int iterations) {
        int[] partition = new int[threadNum + 1];
        for (int i = 0; i < threadNum; i++) {
            partition[i] = i * (iterations / threadNum);
        }
        partition[threadNum] = iterations;

        return partition;
    }

    private static class PiCalculator implements Callable<Double> {

        private final int start;
        private final int end;

        public PiCalculator(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Double call() {
            double sum = 0.0;
            for (int i = start; i < end; i++) {
                sum += Math.pow(-1, i) / (1 + i * 2);
            }

            return sum;
        }

    }

    public static void main(String[] args) {
        int threadNum = Integer.parseInt(args[0]);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Iterations: ");
        int iterations;
        try {
            iterations = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            iterations = 100;
            e.printStackTrace();
        }

        System.out.println(countPi(threadNum, iterations));
    }

}
