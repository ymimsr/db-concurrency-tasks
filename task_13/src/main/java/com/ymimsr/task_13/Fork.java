package com.ymimsr.task_13;

import java.util.concurrent.atomic.AtomicBoolean;

public class Fork {

    private final AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public void free() {
        atomicBoolean.set(true);
    }

    public void take() {
        atomicBoolean.set(false);
    }

    public boolean isFree() {
        return atomicBoolean.get();
    }

}
