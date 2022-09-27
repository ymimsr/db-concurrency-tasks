package com.ymimsr.task_6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public final class Founder {

    private final List<Runnable> workers;
    private final CyclicBarrier BARRIER;

    public Founder(final Company company) {
        BARRIER = new CyclicBarrier(company.getDepartmentsCount(),
                company::showCollaborativeResult);

        this.workers = new ArrayList<>();
        for (int i = 0; i < company.getDepartmentsCount(); i++) {
            workers.add(new Worker(company.getFreeDepartment(i)));
        }
    }

    public void start() {
        for (final Runnable worker : workers) {
            new Thread(worker).start();
        }
    }

    private class Worker implements Runnable {

        private final Department department;

        public Worker(Department department) {
            this.department = department;
        }

        @Override
        public void run() {
            try {
                department.performCalculations();
                BARRIER.await();
            } catch (InterruptedException | BrokenBarrierException exception) {
                exception.printStackTrace();
            }
        }

    }

}
