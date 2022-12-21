package com.ymimsr.task_8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Solution {

    private final int threadNum;
    private final List<FutureTask<Double>> taskList = new ArrayList<>();

    private final AtomicBoolean isCounting = new AtomicBoolean(false);

    private final static int BLOCK_SIZE = 100;

    public Solution(int threadNum) {
        this.threadNum = threadNum;
    }

    public void startCountingPi() {
        isCounting.set(true);
        for (int i = 0; i < threadNum; i++) {
            Callable<Double> subSumCalculator = new PiCalculator(i * BLOCK_SIZE, BLOCK_SIZE, threadNum * BLOCK_SIZE);
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

        private int startI;
        private final int blockSize;
        private final int step;

        public PiCalculator(int startI, int blockSize, int step) {
            this.startI = startI;
            this.blockSize = blockSize;
            this.step = step;
        }

        @Override
        public Double call() {
            double sum = 0.0;

            while (isCounting.get()) {
                int endI = startI + blockSize;
                for (int i = startI; i < endI; i++) {
                    sum += Math.pow(-1, i) / (1 + 2 * i);
                }
                startI += step;
            }

            return sum;
        }
    }

}
