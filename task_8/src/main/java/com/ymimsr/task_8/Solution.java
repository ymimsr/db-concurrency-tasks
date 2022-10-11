package com.ymimsr.task_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Solution {

    private final int threadNum;
    private final List<FutureTask<Double>> taskList = new ArrayList<>();

    private AtomicBoolean isCounting = new AtomicBoolean(false);

    public Solution(int threadNum) {
        this.threadNum = threadNum;
    }

    public void startCountingPi() {
        isCounting.set(true);
        for (int i = 0; i < threadNum; i++) {
            Callable<Double> subSumCalculator = new PiCalculator(threadNum, i);
            FutureTask<Double> subSumTask = new FutureTask<>(subSumCalculator);
            taskList.add(subSumTask);

            new Thread(subSumTask).start();
        }
    }

    public double getPi() {
        isCounting.set(false);
        double sum = 0.0;
        for (FutureTask<Double> task : taskList) {
            try {
                sum += task.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return sum * 4;
    }

    private class PiCalculator implements Callable<Double> {

        private final int step;
        private final int startI;

        public PiCalculator(int step, int startI) {
            this.step = step;
            this.startI = startI;
        }

        @Override
        public Double call() {
            double sum = 0.0;

            for (int i = startI; isCounting.get(); i += step) {
                sum += Math.pow(-1, i) / (1 + 2 * i);
            }

            return sum;
        }
    }

}
